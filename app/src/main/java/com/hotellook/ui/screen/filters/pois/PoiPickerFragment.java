package com.hotellook.ui.screen.filters.pois;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.db.PoiHistoryDBHelper;
import com.hotellook.db.data.PoiHistoryItem;
import com.hotellook.events.DistanceTargetSelectedEvent;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.events.MapPointClickEvent;
import com.hotellook.events.SearchPointClickEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.distancetarget.DistanceTarget;
import com.hotellook.filters.distancetarget.PoiDistanceTarget;
import com.hotellook.filters.distancetarget.PoiHistoryDistanceTarget;
import com.hotellook.filters.distancetarget.SearchPointDistanceTarget;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.filters.items.SortingItem;
import com.hotellook.search.Pois;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.filters.SearchViewKeyboardController;
import com.hotellook.ui.screen.filters.pois.adapter.FixedTypeAdapter;
import com.hotellook.ui.screen.filters.pois.adapter.ListItemBinder;
import com.hotellook.ui.screen.filters.pois.listitems.PoiItemsHolderCreator;
import com.hotellook.ui.screen.filters.pois.listitems.TitleListItem;
import com.hotellook.ui.screen.locationchooser.FilterLocationChooserFragment;
import com.hotellook.ui.screen.searchresults.ViewHorizontalAligner;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.BackAwareEditText;
import com.hotellook.ui.view.TouchyRecyclerView;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.ViewUtils;
import com.squareup.otto.Subscribe;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;
import timber.log.Timber;

public class PoiPickerFragment extends BaseFragment {
    public static final int SOURCE_FILTER = 1;
    public static final int SOURCE_SORTING = 0;
    private View cancel;
    private FixedTypeAdapter defaultAdapter;
    private Subscription defaultDataSubscription;
    private DistanceFilterItem filterItem;
    private Subscription filterSubscription;
    private final Handler handler;
    private int poiPickSource;
    private ToolbarSettings prevToolbarSettings;
    private RecyclerView recyclerView;
    private SearchData searchData;
    private BackAwareEditText searchEditText;
    private SearchViewKeyboardController searchViewKeyboardController;
    private SortingItem sortingItem;
    private Pois sourceData;

    /* renamed from: com.hotellook.ui.screen.filters.pois.PoiPickerFragment.1 */
    class C12751 extends MonkeySafeClickListener {
        C12751() {
        }

        public void onSafeClick(View v) {
            PoiPickerFragment.this.close();
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.pois.PoiPickerFragment.2 */
    class C12762 extends MonkeySafeClickListener {
        C12762() {
        }

        public void onSafeClick(View v) {
            PoiPickerFragment.this.searchEditText.getText().clear();
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.pois.PoiPickerFragment.3 */
    class C12773 implements TextWatcher {
        C12773() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                PoiPickerFragment.this.switchToDefaultState();
                ViewUtils.hideView(PoiPickerFragment.this.cancel);
            } else {
                ViewUtils.showView(PoiPickerFragment.this.cancel);
            }
            if (s.length() > PoiPickerFragment.SOURCE_FILTER) {
                PoiPickerFragment.this.foundMatches(s);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PoiPickSource {
    }

    public PoiPickerFragment() {
        this.handler = new Handler();
        this.filterSubscription = Subscriptions.empty();
    }

    public static PoiPickerFragment create(int poiPickSource) {
        PoiPickerFragment poiPickerFragment = new PoiPickerFragment();
        poiPickerFragment.setPoiPickSource(poiPickSource);
        return poiPickerFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Search search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.searchData = search.searchData();
        this.sourceData = this.searchData.pois();
        Filters filters = search.filters();
        this.filterItem = filters.getGeneralPage().getDistanceFilterItem();
        this.sortingItem = filters.getSortingCategory().getSortingItem();
    }

    public void setPoiPickSource(int poiPickSource) {
        this.poiPickSource = poiPickSource;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(C1178R.layout.fragment_poi_name_selector, container, false);
        this.recyclerView = (RecyclerView) layout.findViewById(C1178R.id.list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new ViewHorizontalAligner(getContext()).alignWithPaddings(this.recyclerView);
        ((TouchyRecyclerView) this.recyclerView).setOnNoChildClickListener(PoiPickerFragment$$Lambda$1.lambdaFactory$(this));
        layout.setOnClickListener(new C12751());
        setUpKeyboardHiderOverlay(layout);
        setUpToolbar();
        AndroidUtils.addPaddingToOffsetNavBarBottom(this.recyclerView);
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.recyclerView);
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(this.recyclerView);
        this.searchViewKeyboardController = new SearchViewKeyboardController(this.searchEditText);
        this.searchEditText.setSelection(this.searchEditText.getText().length());
        HotellookApplication.eventBus().register(this);
        loadDefaultData();
        return layout;
    }

    private void loadDefaultData() {
        this.defaultDataSubscription = new DefaultDataCreator(getContext(), this.searchData, getComponent().eventBus()).prepareItems().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(PoiPickerFragment$$Lambda$2.lambdaFactory$(this), PoiPickerFragment$$Lambda$3.lambdaFactory$());
    }

    /* synthetic */ void lambda$loadDefaultData$0(List listItemBinders) {
        this.defaultAdapter = new FixedTypeAdapter(new PoiItemsHolderCreator(), listItemBinders);
        if (this.recyclerView.getAdapter() == null) {
            this.recyclerView.setAdapter(this.defaultAdapter);
        }
    }

    public void onDestroyView() {
        if (!(this.defaultDataSubscription == null || this.defaultDataSubscription.isUnsubscribed())) {
            this.defaultDataSubscription.unsubscribe();
        }
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), this.prevToolbarSettings);
    }

    public void onMatched(List<ListItemBinder> filteredPois) {
        this.recyclerView.setAdapter(new FixedTypeAdapter(new PoiItemsHolderCreator(), filteredPois));
    }

    @Subscribe
    public void onTargetSelected(DistanceTargetSelectedEvent event) {
        updateHistory(event.distanceTarget);
        this.sortingItem.setDistanceTarget(event.distanceTarget);
        this.filterItem.setDistanceTarget(event.distanceTarget);
        if (this.poiPickSource == 0) {
            this.sortingItem.setAlgoId(C1178R.id.sorting_distance);
            HotellookApplication.eventBus().post(new FiltersSortingChangedEvent(this.sortingItem));
        } else {
            HotellookApplication.eventBus().post(new FiltersChangedEvent());
        }
        getMainActivity().onBackPressed();
    }

    private void updateHistory(DistanceTarget distanceTarget) {
        if (distanceTarget instanceof PoiDistanceTarget) {
            HotellookApplication.getApp().getComponent().getPoiHistoryDBHelper().save(new PoiHistoryItem(((PoiDistanceTarget) distanceTarget).cityId(), ((PoiDistanceTarget) distanceTarget).getPoi(), System.currentTimeMillis()));
        } else if (distanceTarget instanceof PoiHistoryDistanceTarget) {
            PoiHistoryDBHelper databaseHelper = HotellookApplication.getApp().getComponent().getPoiHistoryDBHelper();
            PoiHistoryItem poiHistoryItem = ((PoiHistoryDistanceTarget) distanceTarget).getPoiHistoryItem();
            poiHistoryItem.setVersion(System.currentTimeMillis());
            databaseHelper.update(poiHistoryItem);
        }
    }

    private View inflateSearchView() {
        return LayoutInflater.from(getActivity()).inflate(C1178R.layout.tb_searchview, getMainActivity().getToolbarManager().getToolbar(), false);
    }

    private void setUpToolbar() {
        View searchContainer = inflateSearchView();
        this.searchEditText = (BackAwareEditText) searchContainer.findViewById(C1178R.id.et_search);
        this.searchEditText.setHint(C1178R.string.place_name);
        setUpSearch(searchContainer);
        ToolbarSettings toolbarSettings = new ToolbarSettings().navigationMode(SOURCE_FILTER).bkgColor(getResources().getColor(C1178R.color.date_picker_toolbar_bkg)).toggleColor(getResources().getColor(C1178R.color.dp_toggle)).withCustomView(searchContainer).withShadow();
        this.prevToolbarSettings = getMainActivity().getToolbarManager().getCurrentSettings();
        getMainActivity().getToolbarManager().setUpToolbar(getSupportActionBar(), toolbarSettings);
    }

    private void setUpKeyboardHiderOverlay(View layout) {
        layout.findViewById(C1178R.id.layout_overlay).setOnTouchListener(PoiPickerFragment$$Lambda$4.lambdaFactory$(this));
    }

    /* synthetic */ boolean lambda$setUpKeyboardHiderOverlay$2(View v, MotionEvent event) {
        if (this.searchViewKeyboardController.isKeyboardPossiblyVisible()) {
            this.searchViewKeyboardController.hideKeyboard();
        }
        return false;
    }

    private void setUpSearch(View searchContainer) {
        this.cancel = searchContainer.findViewById(C1178R.id.iv_cancel);
        this.cancel.setOnClickListener(new C12762());
        this.searchEditText.setOnFocusChangeListener(PoiPickerFragment$$Lambda$5.lambdaFactory$(this));
        this.searchEditText.requestFocus();
        this.searchEditText.addTextChangedListener(new C12773());
        this.searchEditText.setOnEditorActionListener(PoiPickerFragment$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$setUpSearch$4(View v, boolean hasFocus) {
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(PoiPickerFragment$$Lambda$14.lambdaFactory$(this), 50);
    }

    /* synthetic */ void lambda$null$3() {
        this.searchEditText.setOnFocusChangeListener(null);
        this.searchViewKeyboardController.showKeyboardWithDelay(this);
    }

    /* synthetic */ boolean lambda$setUpSearch$5(TextView v, int actionId, KeyEvent event) {
        if ((actionId != 3 && actionId != 6 && (event == null || event.getAction() != 0 || event.getKeyCode() != 66)) || AndroidUtils.preventDoubleClick()) {
            return false;
        }
        close();
        return true;
    }

    private void switchToDefaultState() {
        if (this.defaultAdapter != null) {
            this.recyclerView.setAdapter(this.defaultAdapter);
        } else {
            this.recyclerView.setAdapter(null);
        }
    }

    private void close() {
        this.searchViewKeyboardController.hideKeyboard();
        getMainActivity().onBackPressed();
    }

    private void foundMatches(@NonNull CharSequence s) {
        String match = s.toString().toLowerCase();
        if (!this.filterSubscription.isUnsubscribed()) {
            this.filterSubscription.unsubscribe();
        }
        Set<Integer> cityId = this.sourceData.locationIds();
        Iterable observables = new ArrayList(cityId.size());
        for (Integer locationId : cityId) {
            observables.add(Observable.from(this.sourceData.inLocation(locationId.intValue())).filter(PoiPickerFragment$$Lambda$7.lambdaFactory$(match)).map(PoiPickerFragment$$Lambda$8.lambdaFactory$(locationId)));
        }
        Observable.merge(observables).distinct(PoiPickerFragment$$Lambda$9.lambdaFactory$()).map(PoiPickerFragment$$Lambda$10.lambdaFactory$()).toList().doOnNext(PoiPickerFragment$$Lambda$11.lambdaFactory$(this)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(PoiPickerFragment$$Lambda$12.lambdaFactory$(this), PoiPickerFragment$$Lambda$13.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$foundMatches$10(List listItemBinders) {
        if (!listItemBinders.isEmpty()) {
            listItemBinders.add(0, new TitleListItem(getString(C1178R.string.search_results)));
        }
    }

    private void onError(Throwable throwable) {
        Timber.m752d(throwable, "Error while matching pois", new Object[0]);
    }

    @Subscribe
    public void onMapPointClickEvent(MapPointClickEvent event) {
        getMainActivity().showOverlay(FilterLocationChooserFragment.create(this.searchData.searchParams().location()));
    }

    @Subscribe
    public void onSearchPointClickEvent(SearchPointClickEvent event) {
        SearchPointDistanceTarget distanceTarget = new SearchPointDistanceTarget(getContext(), this.searchData.searchParams().location());
        this.sortingItem.setDistanceTarget(distanceTarget);
        this.filterItem.setDistanceTarget(distanceTarget);
        getComponent().eventBus().post(new FiltersChangedEvent());
        getMainActivity().goBack();
    }
}
