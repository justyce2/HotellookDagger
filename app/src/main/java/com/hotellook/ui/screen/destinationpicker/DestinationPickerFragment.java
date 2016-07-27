package com.hotellook.ui.screen.destinationpicker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.callback.CallbackWithTimeout;
import com.hotellook.api.callback.CancelableRetrofitCallback;
import com.hotellook.api.data.DestinationData;
import com.hotellook.api.data.DestinationType;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteData;
import com.hotellook.db.data.DestinationPickerHistoryItem;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.events.MapPointClickEvent;
import com.hotellook.events.NewDestinationEvent;
import com.hotellook.events.NoCitiesOrHotelsEvent;
import com.hotellook.filters.distancetarget.MyLocationDistanceTarget;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.locationchooser.DestinationLocationChooserFragment;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.CircularProgressBar;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;
import com.hotellook.utils.ViewUtils;
import com.j256.ormlite.dao.Dao;
import com.squareup.otto.Subscribe;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import pl.charmas.android.reactivelocation.C1822R;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class DestinationPickerFragment extends BaseFragment implements OnBackPressHandler {
    private static final int ACTIVITY_INDICATOR_DELAY = 200;
    private static final int AUTOCOMPLETE_DELAY = 150;
    private static final int MAX_HISTORY_ITEMS = 10;
    private static final String MESSAGE_TERM = "term";
    private static final int REQUEST_TIMEOUT = 10000;
    private static final int STATE_BAD_CONNECTION = 1;
    private static final int STATE_NO_RESULTS = 2;
    private static final int STATE_RESULTS = 0;
    private CircularProgressBar activityIndicator;
    private DestinationsPickerAdapter adapter;
    private Handler autocompleteRequestHandler;
    private View badConnectionView;
    private View btnCancel;
    private View btnRetry;
    private EditText etSearch;
    private EventBus eventBus;
    private Runnable hideActivityAction;
    private List<DestinationData> historyData;
    private boolean keyboardPossiblyVisible;
    private String language;
    private CancelableRetrofitCallback<AutocompleteData> lastCallback;
    private View notFoundView;
    private boolean preventShowingKeyboard;
    private RecyclerView resultsView;
    private Runnable showActivityAction;

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.12 */
    class AnonymousClass12 extends CallbackWithTimeout<AutocompleteData> {
        AnonymousClass12(long timeout) {
            super(timeout);
        }

        public void success(AutocompleteData autocompleteData, Response response) {
            super.success(autocompleteData, response);
            if (!isCanceled() && DestinationPickerFragment.this.getActivity() != null) {
                if (autocompleteData.getCities().size() + autocompleteData.getHotels().size() == 0) {
                    DestinationPickerFragment.this.setState(DestinationPickerFragment.STATE_NO_RESULTS);
                } else {
                    DestinationPickerFragment.this.setState(0);
                    DestinationPickerFragment.this.adapter.setData(autocompleteData);
                }
                DestinationPickerFragment.this.hideActivityIndicator();
            }
        }

        public void failure(RetrofitError error) {
            super.failure(error);
            if (!isCanceled() && DestinationPickerFragment.this.getActivity() != null) {
                DestinationPickerFragment.this.hideActivityIndicator();
                DestinationPickerFragment.this.setState(DestinationPickerFragment.STATE_BAD_CONNECTION);
            }
        }

        public void onCancel() {
            super.onCancel();
            DestinationPickerFragment.this.hideActivityIndicator();
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.1 */
    class C12221 implements Runnable {
        C12221() {
        }

        public void run() {
            DestinationPickerFragment.this.animateShow(DestinationPickerFragment.this.activityIndicator);
            DestinationPickerFragment.this.animateHide(DestinationPickerFragment.this.btnCancel);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.2 */
    class C12232 implements Runnable {
        C12232() {
        }

        public void run() {
            DestinationPickerFragment.this.animateShow(DestinationPickerFragment.this.btnCancel);
            DestinationPickerFragment.this.animateHide(DestinationPickerFragment.this.activityIndicator);
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.3 */
    class C12243 extends MonkeySafeClickListener {
        C12243() {
        }

        public void onSafeClick(View v) {
            if (DestinationPickerFragment.this.getActivity() != null && DestinationPickerFragment.this.etSearch != null && DestinationPickerFragment.this.etSearch.getText().length() > 0) {
                DestinationPickerFragment.this.retrieveAutocompleteData(DestinationPickerFragment.this.etSearch.getText());
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.4 */
    class C12254 extends MonkeySafeClickListener {
        C12254() {
        }

        public void onSafeClick(View v) {
            DestinationPickerFragment.this.etSearch.getText().clear();
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.5 */
    class C12265 implements OnTouchListener {
        C12265() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            DestinationPickerFragment.this.keyboardPossiblyVisible = true;
            return false;
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.6 */
    class C12286 implements TextWatcher {

        /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.6.1 */
        class C12271 extends Handler {
            C12271() {
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (DestinationPickerFragment.this.etSearch == null || DestinationPickerFragment.this.etSearch.getText().length() != 0) {
                    DestinationPickerFragment.this.retrieveAutocompleteData(msg.getData().getString(DestinationPickerFragment.MESSAGE_TERM));
                }
            }
        }

        C12286() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                DestinationPickerFragment.this.onEmptySearch();
                return;
            }
            if (s.length() > 0) {
                DestinationPickerFragment.this.btnCancel.setVisibility(0);
                DestinationPickerFragment.this.activityIndicator.setVisibility(0);
            }
            if (s.length() > DestinationPickerFragment.STATE_BAD_CONNECTION) {
                if (DestinationPickerFragment.this.autocompleteRequestHandler == null) {
                    DestinationPickerFragment.this.autocompleteRequestHandler = createAutocompleteRequestHandler();
                }
                if (DestinationPickerFragment.this.lastCallback != null) {
                    DestinationPickerFragment.this.lastCallback.cancel();
                }
                DestinationPickerFragment.this.autocompleteRequestHandler.removeCallbacksAndMessages(null);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString(DestinationPickerFragment.MESSAGE_TERM, s.toString());
                message.setData(bundle);
                DestinationPickerFragment.this.autocompleteRequestHandler.sendMessageDelayed(message, 150);
            }
        }

        private Handler createAutocompleteRequestHandler() {
            return new C12271();
        }

        public void afterTextChanged(Editable s) {
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.7 */
    class C12307 implements OnFocusChangeListener {

        /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.7.1 */
        class C12291 implements Runnable {
            C12291() {
            }

            public void run() {
                DestinationPickerFragment.this.etSearch.setOnFocusChangeListener(null);
                DestinationPickerFragment.this.showKeyboardWithDelay();
            }
        }

        C12307() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            DestinationPickerFragment.this.etSearch.post(new C12291());
        }
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.8 */
    class C12318 implements OnTouchListener {
        C12318() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (!(DestinationPickerFragment.this.notFoundView.getVisibility() == 0 || DestinationPickerFragment.this.badConnectionView.getVisibility() == 0 || !DestinationPickerFragment.this.keyboardPossiblyVisible)) {
                DestinationPickerFragment.this.hideKeyboard();
            }
            return false;
        }
    }

    public interface OnDestinationSelectListener {
        void onDestinationSelect(DestinationData destinationData);
    }

    /* renamed from: com.hotellook.ui.screen.destinationpicker.DestinationPickerFragment.9 */
    class C12329 implements OnDestinationSelectListener {
        C12329() {
        }

        public void onDestinationSelect(DestinationData data) {
            if (DestinationPickerFragment.this.getActivity() != null) {
                HotellookApplication.eventBus().post(new NewDestinationEvent(data.getCityId(), data.getHotelId(), data.getType(), DestinationPickerFragment.this.language));
                DestinationPickerFragment.this.updateSearchFormWithNewDestination(data);
                DestinationPickerFragment.this.addDestinationToHistory(data);
                DestinationPickerFragment.this.returnOnPrevScreen();
            }
        }
    }

    public DestinationPickerFragment() {
        this.showActivityAction = new C12221();
        this.hideActivityAction = new C12232();
        this.keyboardPossiblyVisible = true;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.language = AndroidUtils.getLanguage();
        this.eventBus = getComponent().eventBus();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(16);
        if (savedInstanceState == null) {
            getMainActivity().lockDrawer();
        }
        View searchView = inflateSearchView();
        setUpToolbar(searchView);
        ViewGroup layout = (ViewGroup) inflater.inflate(C1178R.layout.fragment_destination_picker, container, false);
        setUpKeyboardHiderOverlay(layout);
        this.notFoundView = layout.findViewById(C1178R.id.ph_not_found);
        this.badConnectionView = layout.findViewById(C1178R.id.ph_bad_connection);
        this.btnRetry = layout.findViewById(C1178R.id.btn_retry);
        this.resultsView = (RecyclerView) layout.findViewById(C1178R.id.rv_results);
        this.etSearch = (EditText) searchView.findViewById(C1178R.id.et_search);
        this.btnCancel = searchView.findViewById(C1178R.id.iv_cancel);
        this.activityIndicator = (CircularProgressBar) searchView.findViewById(C1178R.id.activity_indicator);
        compensateBottomScreensOffset(layout.findViewById(C1178R.id.place_holder_container));
        this.notFoundView.setVisibility(4);
        this.badConnectionView.setVisibility(4);
        this.activityIndicator.setVisibility(4);
        this.activityIndicator.setAlpha(0.0f);
        this.btnCancel.setVisibility(4);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.notFoundView);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.badConnectionView);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.resultsView);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.resultsView);
        setUpRecyclerView();
        setUpSearchView();
        setUpCancelBtn();
        setUpRetryBtn();
        this.eventBus.register(this);
        return layout;
    }

    private void compensateBottomScreensOffset(View placeHolderContainer) {
        ((MarginLayoutParams) placeHolderContainer.getLayoutParams()).bottomMargin = -(AndroidUtils.getBottomNavHeight(getContext()) + AndroidUtils.getNavBarBottomHeight());
    }

    private void setUpRecyclerView() {
        this.resultsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.resultsView.setHasFixedSize(false);
        setUpAdapter();
    }

    private void setUpRetryBtn() {
        this.btnRetry.setOnClickListener(new C12243());
    }

    private void setUpCancelBtn() {
        this.btnCancel.setOnClickListener(new C12254());
    }

    private View inflateSearchView() {
        return LayoutInflater.from(getActivity()).inflate(C1178R.layout.view_dp_search, getMainActivity().getToolbarManager().getToolbar(), false);
    }

    private void setUpSearchView() {
        this.etSearch.setOnTouchListener(new C12265());
        this.etSearch.addTextChangedListener(new C12286());
        this.etSearch.setOnFocusChangeListener(new C12307());
        this.etSearch.requestFocus();
    }

    private void setUpKeyboardHiderOverlay(ViewGroup layout) {
        layout.findViewById(C1178R.id.layout_overlay).setOnTouchListener(new C12318());
    }

    private void setUpToolbar(View searchView) {
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), new ToolbarSettings().withShadow().navigationMode(STATE_BAD_CONNECTION).bkgColor(getResources().getColor(C1178R.color.toolbar_gray_bkg)).toggleColor(getResources().getColor(C1178R.color.dp_toggle)).withCustomView(searchView));
    }

    private void setUpAdapter() {
        this.adapter = new DestinationsPickerAdapter(getComponent().eventBus());
        this.adapter.setOnDestinationSelectListener(new C12329());
        this.resultsView.setAdapter(this.adapter);
        setDefaultDataForAdapter();
    }

    private void updateSearchFormWithNewDestination(DestinationData data) {
        SearchFormData searchFormData = new SearchFormData(getApplication(), getComponent().getSearchFormPreferences());
        if (data.getType() == DestinationType.CITY) {
            searchFormData.setCity(data);
        } else {
            searchFormData.setHotel(data);
        }
        searchFormData.saveData();
    }

    private void returnOnPrevScreen() {
        hideKeyboard();
        getActivity().onBackPressed();
    }

    @Subscribe
    public void onMyLocationClick(DistanceTargetSelectedEvent event) {
        SearchFormData searchFormData = new SearchFormData(getContext(), getComponent().getSearchFormPreferences());
        searchFormData.updateWithUserLocationDestination(((MyLocationDistanceTarget) event.distanceTarget).location);
        searchFormData.saveData();
        returnOnPrevScreen();
    }

    private void onEmptySearch() {
        if (this.lastCallback != null) {
            this.lastCallback.cancel();
        }
        if (this.autocompleteRequestHandler != null) {
            this.autocompleteRequestHandler.removeCallbacksAndMessages(null);
        }
        removeActivityIndicatorCallbacks();
        setState(0);
        setDefaultDataForAdapter();
        this.btnCancel.setVisibility(4);
        this.activityIndicator.setVisibility(4);
        this.activityIndicator.setAlpha(0.0f);
    }

    private void animateHide(View view) {
        if (view.getAlpha() > 0.0f) {
            view.animate().alpha(0.0f).setListener(null).setDuration(200);
        }
    }

    private void animateShow(View view) {
        if (view.getAlpha() < 1.0f) {
            view.animate().alpha(1.0f).setListener(null).setDuration(200);
        }
    }

    private void setState(int state) {
        switch (state) {
            case C1822R.styleable.SignInButton_buttonSize /*0*/:
                ViewUtils.showView(this.resultsView);
                ViewUtils.hideView(this.notFoundView);
                ViewUtils.hideView(this.badConnectionView);
            case STATE_BAD_CONNECTION /*1*/:
                ViewUtils.hideView(this.resultsView);
                ViewUtils.hideView(this.notFoundView);
                ViewUtils.showView(this.badConnectionView);
            case STATE_NO_RESULTS /*2*/:
                ViewUtils.hideView(this.resultsView);
                ViewUtils.showView(this.notFoundView);
                ViewUtils.hideView(this.badConnectionView);
                HotellookApplication.eventBus().post(new NoCitiesOrHotelsEvent());
            default:
        }
    }

    private void removeActivityIndicatorCallbacks() {
        this.activityIndicator.removeCallbacks(this.showActivityAction);
        this.activityIndicator.removeCallbacks(this.hideActivityAction);
    }

    private void showKeyboardWithDelay() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (DestinationPickerFragment.this.getActivity() != null) {
                    DestinationPickerFragment.this.showKeyboard();
                }
            }
        }, 400);
    }

    private void setDefaultDataForAdapter() {
        if (this.historyData == null) {
            retrieveHistoryDataFromDb();
        }
        this.adapter.setData(this.historyData, getComponent().nearestLocationsProvider().nearestLocations());
    }

    private void retrieveHistoryDataFromDb() {
        this.historyData = new ArrayList();
        List<DestinationPickerHistoryItem> historyItems = null;
        try {
            historyItems = getDestinationHistoryDao().queryForAll();
        } catch (SQLException e) {
            Timber.tag("db").m707e("Error while retrieving history items", new Object[0]);
        }
        if (historyItems != null && historyItems.size() > 0) {
            sortHistoryByDescendingTimestamp(historyItems);
            if (historyItems.size() > MAX_HISTORY_ITEMS) {
                try {
                    getDestinationHistoryDao().delete(historyItems.get(historyItems.size() - 1));
                    historyItems.remove(historyItems.get(historyItems.size() - 1));
                } catch (SQLException e2) {
                    Timber.tag("db").m707e("Error while deleting history item", new Object[0]);
                }
            }
            for (DestinationPickerHistoryItem historyItem : historyItems) {
                this.historyData.add(new DestinationData(historyItem));
            }
        }
    }

    private Dao<DestinationPickerHistoryItem, Integer> getDestinationHistoryDao() throws SQLException {
        return getMainActivity().getHelper().getDestinationPickerHistoryDao();
    }

    private void sortHistoryByDescendingTimestamp(List<DestinationPickerHistoryItem> historyItems) {
        Collections.sort(historyItems, new Comparator<DestinationPickerHistoryItem>() {
            public int compare(DestinationPickerHistoryItem lhs, DestinationPickerHistoryItem rhs) {
                return (int) (rhs.getTimestamp() - lhs.getTimestamp());
            }
        });
    }

    private void addDestinationToHistory(DestinationData data) {
        try {
            DestinationPickerHistoryItem item = new DestinationPickerHistoryItem(data);
            List<DestinationPickerHistoryItem> sameItemsInDb = getDestinationHistoryDao().queryForEq("destinationId", item.getDestinationId());
            if (sameItemsInDb == null || sameItemsInDb.size() <= 0) {
                getDestinationHistoryDao().create(item);
                return;
            }
            ((DestinationPickerHistoryItem) sameItemsInDb.get(0)).update(item);
            getDestinationHistoryDao().update(sameItemsInDb.get(0));
        } catch (SQLException e) {
            Timber.tag("db").m707e("Error while adding new history item", new Object[0]);
        }
    }

    private void retrieveAutocompleteData(CharSequence term) {
        if (!(this.lastCallback == null || this.lastCallback.isFinished())) {
            this.lastCallback.cancel();
        }
        this.lastCallback = new AnonymousClass12(10000);
        getComponent().getHotellookService().autocomplete(term.toString(), this.language, this.lastCallback);
        showActivityIndicator();
    }

    private void showActivityIndicator() {
        removeActivityIndicatorCallbacks();
        this.activityIndicator.postDelayed(this.showActivityAction, 100);
    }

    private void hideActivityIndicator() {
        removeActivityIndicatorCallbacks();
        this.activityIndicator.postDelayed(this.hideActivityAction, 200);
    }

    private void hideKeyboard() {
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.etSearch.getWindowToken(), 0);
        this.keyboardPossiblyVisible = false;
    }

    private void showKeyboard() {
        if (!this.preventShowingKeyboard) {
            ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(this.etSearch, STATE_BAD_CONNECTION);
            this.keyboardPossiblyVisible = true;
        }
    }

    public boolean onBackPressedHandled() {
        this.preventShowingKeyboard = true;
        hideKeyboard();
        return false;
    }

    public void onDestroyView() {
        this.eventBus.unregister(this);
        super.onDestroyView();
    }

    @Subscribe
    public void onMapPointItemClick(MapPointClickEvent event) {
        getMainActivity().showFragment(DestinationLocationChooserFragment.create(new SearchFormData(getContext(), getComponent().getSearchFormPreferences()).getLocation()));
    }
}
