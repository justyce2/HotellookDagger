package com.hotellook.ui.screen.filters.hotelname;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.db.data.HotelNameHistory;
import com.hotellook.db.data.PoiHistoryItem;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.HistoryHotelNameSelectedEvent;
import com.hotellook.events.HotelNameSelectedEvent;
import com.hotellook.events.HotelNamesMatchedEvent;
import com.hotellook.filters.items.HotelNameFilterItem;
import com.hotellook.filters.items.criterion.HotelNameCriterion;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.filters.SearchViewKeyboardController;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.BackAwareEditText;
import com.hotellook.ui.view.TouchyRecyclerView;
import com.hotellook.ui.view.TouchyRecyclerView.OnNoChildClickListener;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.squareup.otto.Subscribe;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HotelNameSelectorFragment extends BaseFragment {
    public HotelHistoryAdapter adapter;
    private HotelNamesAdapter mAdapter;
    private View mCancel;
    private Cursor mCursor;
    private ExecutorService mExecutor;
    private Handler mHandler;
    private HotelNameFilterItem mHotelNameFilterItem;
    private CloseableIterator<HotelNameHistory> mIterator;
    private ToolbarSettings mPrevToolbarSettings;
    private RecyclerView mRecyclerView;
    private BackAwareEditText mSearch;
    private SearchViewKeyboardController mSearchViewKeyboardController;
    private List<HotelData> mSourceData;

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.1 */
    class C12631 implements Runnable {
        final /* synthetic */ String val$hotelName;

        C12631(String str) {
            this.val$hotelName = str;
        }

        public void run() {
            try {
                HotelNameSelectorFragment.this.getMainActivity().getHelper().getHotelNameHistoryDao().createOrUpdate(new HotelNameHistory(this.val$hotelName, System.currentTimeMillis()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.2 */
    class C12642 implements OnNoChildClickListener {
        C12642() {
        }

        public void onNoChildClick() {
            HotelNameSelectorFragment.this.deliverResultAndClose(HotelNameSelectorFragment.this.mSearch.getText().toString());
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.3 */
    class C12653 implements OnTouchListener {
        C12653() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (HotelNameSelectorFragment.this.mSearchViewKeyboardController.isKeyboardPossiblyVisible()) {
                HotelNameSelectorFragment.this.mSearchViewKeyboardController.hideKeyboard();
            }
            return false;
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.4 */
    class C12664 extends MonkeySafeClickListener {
        C12664() {
        }

        public void onSafeClick(View v) {
            HotelNameSelectorFragment.this.mSearch.getText().clear();
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.5 */
    class C12685 implements OnFocusChangeListener {

        /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.5.1 */
        class C12671 implements Runnable {
            C12671() {
            }

            public void run() {
                HotelNameSelectorFragment.this.mSearch.setOnFocusChangeListener(null);
                HotelNameSelectorFragment.this.mSearchViewKeyboardController.showKeyboardWithDelay(HotelNameSelectorFragment.this);
            }
        }

        C12685() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            HotelNameSelectorFragment.this.mHandler.removeCallbacksAndMessages(null);
            HotelNameSelectorFragment.this.mHandler.postDelayed(new C12671(), 50);
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment.6 */
    class C12696 implements TextWatcher {
        C12696() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                ViewUtils.hideView(HotelNameSelectorFragment.this.mCancel);
            } else {
                ViewUtils.showView(HotelNameSelectorFragment.this.mCancel);
            }
            if (s.length() > 1) {
                HotelNameSelectorFragment.this.foundMatches(s);
            } else {
                HotelNameSelectorFragment.this.showHistoryState();
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public HotelNameSelectorFragment() {
        this.mHandler = new Handler();
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    public static Fragment create(HotelNameFilterItem filterItem, List<HotelData> sourceData) {
        HotelNameSelectorFragment fragment = new HotelNameSelectorFragment();
        fragment.setHotelNameFilterItem(filterItem);
        fragment.setSourceData(sourceData);
        return fragment;
    }

    public void setHotelNameFilterItem(HotelNameFilterItem hotelNameFilterItem) {
        this.mHotelNameFilterItem = hotelNameFilterItem;
    }

    public void setSourceData(List<HotelData> sourceData) {
        this.mSourceData = sourceData;
    }

    private View inflateSearchView() {
        return LayoutInflater.from(getActivity()).inflate(C1178R.layout.tb_searchview, getMainActivity().getToolbarManager().getToolbar(), false);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(C1178R.layout.fragment_hotel_name_selector, container, false);
        this.mRecyclerView = (RecyclerView) layout.findViewById(C1178R.id.list);
        setUpKeyboardHiderOverlay(layout);
        setUpToolbar();
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.mRecyclerView);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.mRecyclerView);
        setUpRecyclerView();
        this.mSearchViewKeyboardController = new SearchViewKeyboardController(this.mSearch);
        this.mSearch.setSelection(this.mSearch.getText().length());
        HotellookApplication.eventBus().register(this);
        return layout;
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
        resetDBConnection();
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), this.mPrevToolbarSettings);
        createHistoryAdapter().clean();
    }

    @Subscribe
    public void onMatchesFound(HotelNamesMatchedEvent event) {
        this.mAdapter = new HotelNamesAdapter(getComponent().eventBus());
        this.mAdapter.setData(event.hotels);
        this.mRecyclerView.setAdapter(this.mAdapter);
        showContent();
    }

    @Subscribe
    public void onNameSelected(HotelNameSelectedEvent event) {
        deliverResultAndClose(event.hotelName);
    }

    @Subscribe
    public void onNameSelectedFromHistory(HistoryHotelNameSelectedEvent event) {
        deliverResultAndClose(event.hotelName);
    }

    private void deliverResultAndClose(String hotelName) {
        this.mSearchViewKeyboardController.hideKeyboard();
        getActivity().onBackPressed();
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
        if (hotelName.length() > 1) {
            this.mHotelNameFilterItem.setHotelName(hotelName);
            HotellookApplication.eventBus().post(new FiltersChangedEvent());
            new Thread(new C12631(hotelName)).start();
        }
    }

    private void showContent() {
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    private void showHistoryState() {
        this.mRecyclerView.setAdapter(createHistoryAdapter());
    }

    private void setUpRecyclerView() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((TouchyRecyclerView) this.mRecyclerView).setOnNoChildClickListener(new C12642());
        this.mRecyclerView.setHasFixedSize(true);
        showHistoryState();
    }

    private HotelHistoryAdapter createHistoryAdapter() {
        resetDBConnection();
        this.adapter = null;
        Dao<HotelNameHistory, Integer> historyDao = getMainActivity().getHelper().getHotelNameHistoryDao();
        try {
            this.mIterator = historyDao.iterator(historyDao.queryBuilder().orderBy(PoiHistoryItem.VERSION, false).prepare());
            this.mCursor = ((AndroidDatabaseResults) this.mIterator.getRawResults()).getRawCursor();
            this.adapter = new HotelHistoryAdapter(this.mCursor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.adapter;
    }

    private void resetDBConnection() {
        if (this.mIterator != null) {
            this.mIterator.closeQuietly();
        }
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
    }

    private void setUpToolbar() {
        View searchContainer = inflateSearchView();
        this.mSearch = (BackAwareEditText) searchContainer.findViewById(C1178R.id.et_search);
        setUpSearch(searchContainer);
        ToolbarSettings toolbarSettings = new ToolbarSettings().navigationMode(1).bkgColor(getResources().getColor(C1178R.color.date_picker_toolbar_bkg)).toggleColor(getResources().getColor(C1178R.color.dp_toggle)).withCustomView(searchContainer).withShadow();
        this.mPrevToolbarSettings = getMainActivity().getToolbarManager().getCurrentSettings();
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), toolbarSettings);
    }

    private void setUpKeyboardHiderOverlay(View layout) {
        layout.findViewById(C1178R.id.layout_overlay).setOnTouchListener(new C12653());
    }

    private void setUpSearch(View searchContainer) {
        this.mCancel = searchContainer.findViewById(C1178R.id.iv_cancel);
        this.mCancel.setOnClickListener(new C12664());
        this.mSearch.setOnFocusChangeListener(new C12685());
        this.mSearch.requestFocus();
        this.mSearch.addTextChangedListener(new C12696());
        this.mSearch.setOnEditorActionListener(HotelNameSelectorFragment$$Lambda$1.lambdaFactory$(this));
        this.mSearch.setText(this.mHotelNameFilterItem.getHotelName());
    }

    /* synthetic */ boolean lambda$setUpSearch$0(TextView v, int actionId, KeyEvent event) {
        if ((actionId != 3 && actionId != 6 && (event == null || event.getAction() != 0 || event.getKeyCode() != 66)) || AndroidUtils.preventDoubleClick()) {
            return false;
        }
        deliverResultAndClose(v.getText().toString());
        return true;
    }

    private void foundMatches(@NonNull CharSequence s) {
        this.mExecutor.submit(HotelNameSelectorFragment$$Lambda$2.lambdaFactory$(this, new HotelNameCriterion(s.toString())));
    }

    /* synthetic */ void lambda$foundMatches$1(HotelNameCriterion criterion) {
        List<HotelData> filtered = new ArrayList();
        for (HotelData data : this.mSourceData) {
            if (criterion.passes(data)) {
                filtered.add(data);
            }
        }
        HotellookApplication.eventBus().post(new HotelNamesMatchedEvent(filtered));
    }
}
