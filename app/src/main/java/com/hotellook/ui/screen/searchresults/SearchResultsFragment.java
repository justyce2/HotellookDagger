package com.hotellook.ui.screen.searchresults;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.api.data.DestinationType;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.events.FilteringFinishedEvent;
import com.hotellook.events.FiltersResetEvent;
import com.hotellook.events.FiltersSortingChangedEvent;
import com.hotellook.events.NearbyCitiesSearchRequestEvent;
import com.hotellook.events.OpenFiltersEvent;
import com.hotellook.events.OpenHotelDetailsEvent;
import com.hotellook.events.OpenSortingMenuEvent;
import com.hotellook.events.OverlayClosedEvent;
import com.hotellook.events.ResultsScrolledToTopEvent;
import com.hotellook.events.ResultsSwitchedToListEvent;
import com.hotellook.events.ResultsSwitchedToMapEvent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.events.UnbreakableClusterClickEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.OnBackPressHandler;
import com.hotellook.ui.screen.common.BaseMapFragment;
import com.hotellook.ui.screen.filters.FilterFragment;
import com.hotellook.ui.screen.filters.FilterFragmentSideMenu;
import com.hotellook.ui.screen.filters.FilterOpenSource;
import com.hotellook.ui.screen.filters.presenters.SortingPresenter;
import com.hotellook.ui.screen.hotel.HotelFragment;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.searchprogress.SearchProgressFragment;
import com.hotellook.ui.screen.searchresults.filtercontrols.FilterControlsPresenter;
import com.hotellook.ui.screen.searchresults.filtercontrols.FilterControlsPresenterFactory;
import com.hotellook.ui.screen.searchresults.map.ResultsMapView;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.appbar.NestedParentRelativeLayout;
import com.hotellook.ui.view.viewmovers.ScrollableViewsSynchronizer;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.Animators;
import com.hotellook.utils.ParamsToStringFormatter;
import com.squareup.otto.Subscribe;
import java.util.List;
import me.zhanghai.android.materialprogressbar.C1759R;

public class SearchResultsFragment extends BaseMapFragment implements OnBackPressHandler {
    public static final String RIGHT_FILTERS_FRAGMENT_TAG = "Filters";
    private static final int STATE_LIST = 1;
    private static final int STATE_MAP = 2;
    public static final int TOOLBAR_ANIMATION_DURATION = 200;
    private Filters filters;
    private FilterControlsPresenter filtersControlsPresenter;
    private GoogleApiClient googleApiClient;
    private HotelsListView hotelsListView;
    private ResultsMapView mapResultsView;
    private int resultsState;
    @Nullable
    private ScrollableViewsSynchronizer scrollableViewsSynchronizer;
    private Search search;
    private SearchData searchData;
    private SearchParams searchParams;
    private long searchTimestamp;
    private Animator stateAnimator;
    private View toolbarLayout;
    private View toolbarStateBtn;
    private TextView toolbarStateBtnText;
    private ImageView toolbarStateIcon;
    private TextView toolbarSubtitle;
    private TextView toolbarTitle;
    private boolean underOtherFragment;

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsFragment.1 */
    class C13591 implements OnGlobalLayoutListener {
        final /* synthetic */ View val$view;

        C13591(View view) {
            this.val$view = view;
        }

        public void onGlobalLayout() {
            AndroidUtils.removeOnGlobalLayoutListener(this.val$view, this);
            Toolbar toolbar = SearchResultsFragment.this.getMainActivity().getToolbarManager().getToolbar();
            int toolbarHeight = toolbar.getHeight();
            if (SearchResultsFragment.this.hotelsListView != null) {
                SearchResultsFragment.this.scrollableViewsSynchronizer = ScrollableViewsSynchronizer.with(SearchResultsFragment.this.hotelsListView.recyclerView()).addViewToTranslateAndHide((View) toolbar.getParent(), toolbar, -toolbarHeight).addViewToTranslateAndHide(SearchResultsFragment.this.getMainActivity().getToolbarManager().shadow(), -toolbarHeight);
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsFragment.2 */
    class C13602 extends MonkeySafeClickListener {
        C13602() {
        }

        public void onSafeClick(View v) {
            if (SearchResultsFragment.this.resultsState == SearchResultsFragment.STATE_MAP) {
                HotellookApplication.eventBus().post(new ResultsSwitchedToListEvent());
            } else {
                HotellookApplication.eventBus().post(new ResultsSwitchedToMapEvent());
            }
            SearchResultsFragment.this.switchState();
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsFragment.3 */
    class C13613 implements OnGlobalLayoutListener {
        C13613() {
        }

        public void onGlobalLayout() {
            if (SearchResultsFragment.this.getActivity() != null) {
                AndroidUtils.removeOnGlobalLayoutListener(SearchResultsFragment.this.toolbarSubtitle, this);
                SearchResultsFragment.this.toolbarLayout.measure(MeasureSpec.makeMeasureSpec(SearchResultsFragment.this.getToolbar().getWidth() - SearchResultsFragment.this.getToolbar().getChildAt(0).getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                int subtitleExpectedWidthWithVisibleBtnText = SearchResultsFragment.this.toolbarLayout.getMeasuredWidth() - ((MarginLayoutParams) SearchResultsFragment.this.toolbarStateBtn.getLayoutParams()).rightMargin;
                SearchResultsFragment.this.toolbarSubtitle.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                if (SearchResultsFragment.this.toolbarSubtitle.getMeasuredWidth() > subtitleExpectedWidthWithVisibleBtnText) {
                    SearchResultsFragment.this.toolbarStateBtnText.setVisibility(8);
                }
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsFragment.4 */
    class C13624 extends AnimatorListenerAdapter {
        C13624() {
        }

        public void onAnimationEnd(Animator animation) {
            if (SearchResultsFragment.this.hasGMS()) {
                SearchResultsFragment.this.mapResultsView.onMapStateDisabled();
            }
        }
    }

    /* renamed from: com.hotellook.ui.screen.searchresults.SearchResultsFragment.5 */
    class C13635 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$state;

        C13635(int i) {
            this.val$state = i;
        }

        public void onAnimationEnd(Animator animation) {
            if (SearchResultsFragment.this.getActivity() != null) {
                SearchResultsFragment.this.toolbarStateBtnText.setText(this.val$state == SearchResultsFragment.STATE_MAP ? SearchResultsFragment.this.getString(C1178R.string.results_state_list) : SearchResultsFragment.this.getString(C1178R.string.results_state_map));
                SearchResultsFragment.this.toolbarStateIcon.setImageResource(this.val$state == SearchResultsFragment.STATE_MAP ? C1178R.drawable.ic_results_list : C1178R.drawable.ic_results_map);
            }
        }
    }

    static class Snapshot {
        final Object filterControlsState;
        final int filtersHash;
        final Object listViewSavedState;
        final Object mapViewSavedState;
        final int state;
        final boolean underOverFragment;

        Snapshot(int state, Object filterControlsState, int filtersHash, Object mapViewSavedState, Object listViewSavedState, boolean underOtherFragment) {
            this.state = state;
            this.filterControlsState = filterControlsState;
            this.filtersHash = filtersHash;
            this.mapViewSavedState = mapViewSavedState;
            this.listViewSavedState = listViewSavedState;
            this.underOverFragment = underOtherFragment;
        }
    }

    public SearchResultsFragment() {
        this.resultsState = STATE_LIST;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.searchParams = this.search.searchParams();
        this.searchData = this.search.searchData();
        this.searchTimestamp = this.search.startTimestamp();
        this.filters = this.search.filters();
    }

    protected View inflateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(C1178R.layout.fragment_search_results, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMainActivity().unlockDrawer();
        this.filtersControlsPresenter = FilterControlsPresenterFactory.create(getActivity(), hasGMS());
        this.hotelsListView = (HotelsListView) view.findViewById(C1178R.id.results_view);
        NestedParentRelativeLayout layout = (NestedParentRelativeLayout) view;
        View btnLocation = view.findViewById(C1178R.id.location);
        this.filtersControlsPresenter.attachView(view);
        if (!this.underOtherFragment) {
            setUpToolbar();
        }
        this.mapResultsView = (ResultsMapView) view.findViewById(C1178R.id.results_map);
        this.mapResultsView.setRevealedMarkerTopOffset(this.filtersControlsPresenter.additionalTopOffsetForMap());
        this.mapResultsView.setSearchParams(this.searchParams);
        if (hasGMS()) {
            onMapViewCreated(this.mapResultsView.getMap(), savedInstanceState);
            this.mapResultsView.setLocationProvider(this.googleApiClient);
        }
        if (!hasGMS()) {
            btnLocation.setVisibility(8);
        }
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((Snapshot) initialSnapshot());
        }
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(layout);
        if (AndroidUtils.isPhone(getContext())) {
            AndroidUtils.addPaddingToOffsetToolbar(this.mapResultsView);
        }
        HotellookApplication.eventBus().register(this);
        Context context = getContext();
        if (AndroidUtils.isPhone(context)) {
            setUpScrollableToolbar(view);
        }
        if (AndroidUtils.isTablet(context) && AndroidUtils.isLandscape(context)) {
            getChildFragmentManager().beginTransaction().add((int) C1178R.id.filters_fragment_container, new FilterFragmentSideMenu()).commit();
        }
    }

    private void addFiltersToRightMenu() {
        MainActivity mainActivity = getMainActivity();
        if (mainActivity != null) {
            mainActivity.unlockRightDrawer();
            mainActivity.setFragmentToRightDrawer(FilterFragmentSideMenu.create(FilterOpenSource.LIST), RIGHT_FILTERS_FRAGMENT_TAG);
        }
    }

    private void setUpScrollableToolbar(View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new C13591(view));
    }

    private void openFiltersFragment(FilterOpenSource source) {
        this.hotelsListView.recyclerView().stopScroll();
        this.scrollableViewsSynchronizer.cancelAnimation();
        showOverlay(FilterFragment.create(source));
    }

    private void showOverlay(Fragment fragment) {
        this.underOtherFragment = true;
        getMainActivity().showOverlay(fragment);
    }

    public void onStart() {
        super.onStart();
        Context context = getContext();
        if (AndroidUtils.isTablet(context) && AndroidUtils.isPortrait(context)) {
            addFiltersToRightMenu();
        }
    }

    public void onStop() {
        getMainActivity().clearRightDrawer();
        super.onStop();
    }

    public void onDestroyView() {
        if (this.scrollableViewsSynchronizer != null) {
            this.scrollableViewsSynchronizer.cancelAnimation();
        }
        this.filtersControlsPresenter.onDetachFromView();
        HotellookApplication.eventBus().unregister(this);
        clearReferencesToImages();
        getMainActivity().lockRightDrawer();
        super.onDestroyView();
    }

    private void clearReferencesToImages() {
        this.hotelsListView = null;
    }

    private int getFiltersHash() {
        return this.filters.hashCode();
    }

    private void setUpToolbar() {
        this.toolbarLayout = LayoutInflater.from(getContext()).inflate(C1178R.layout.results_toolbar, getToolbar(), false);
        this.toolbarStateBtnText = (TextView) this.toolbarLayout.findViewById(C1178R.id.state_btn_text);
        this.toolbarTitle = (TextView) this.toolbarLayout.findViewById(C1759R.id.title);
        this.toolbarSubtitle = (TextView) this.toolbarLayout.findViewById(C1178R.id.subtitle);
        this.toolbarStateBtn = this.toolbarLayout.findViewById(C1178R.id.state_btn);
        this.toolbarStateIcon = (ImageView) this.toolbarLayout.findViewById(C1178R.id.state_icon);
        setUpToolbarContent();
        MainActivity activity = getMainActivity();
        ToolbarSettings toolbarSettings = new ToolbarSettings().navigationMode(STATE_LIST).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), 17170443)).withCustomView(this.toolbarLayout);
        if (AndroidUtils.isPhone(getContext())) {
            toolbarSettings.withShadow();
        }
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), toolbarSettings);
    }

    private void setUpToolbarContent() {
        if (this.searchParams.isDestinationTypeUserLocation()) {
            this.toolbarTitle.setText(C1178R.string.my_location);
        } else if (this.searchParams.isDestinationTypeNearbyCities()) {
            TextView textView = this.toolbarTitle;
            Object[] objArr = new Object[STATE_LIST];
            objArr[0] = cityName();
            textView.setText(getString(C1178R.string.neigbour_title, objArr));
        } else if (this.searchParams.isDestinationTypeMapPoint()) {
            this.toolbarTitle.setText(C1178R.string.search_type_name_location);
        } else {
            this.toolbarTitle.setText(cityName());
        }
        this.toolbarSubtitle.setText(new ParamsToStringFormatter(getContext()).convertToResultsToolbarFormat(this.searchParams));
        this.toolbarStateBtn.setOnClickListener(new C13602());
        if (AndroidUtils.isPhone(getContext())) {
            hideStateBtnTxtIfSubtitleNotFits();
        }
    }

    private String cityName() {
        return this.searchData.locations().getById(this.searchParams.locationId()).getCity();
    }

    private void hideStateBtnTxtIfSubtitleNotFits() {
        this.toolbarSubtitle.getViewTreeObserver().addOnGlobalLayoutListener(new C13613());
    }

    public boolean onBackPressedHandled() {
        if (this.resultsState == STATE_MAP) {
            switchState(STATE_LIST);
            return true;
        }
        MainActivity mainActivity = getMainActivity();
        if (!mainActivity.isRightDrawerOpened()) {
            return false;
        }
        mainActivity.closeRightDrawer();
        return true;
    }

    private void switchState() {
        if (this.resultsState == STATE_MAP) {
            switchState(STATE_LIST);
        } else {
            switchState(STATE_MAP);
        }
    }

    private void switchState(int state) {
        switchState(state, false);
    }

    private void switchState(int state, boolean onCreate) {
        int i = 0;
        if (getMainActivity() != null) {
            if (this.stateAnimator != null && this.stateAnimator.isRunning()) {
                this.stateAnimator.cancel();
            }
            switch (state) {
                case STATE_LIST /*1*/:
                    if (AndroidUtils.isPhone(getContext())) {
                        getMainActivity().getToolbarManager().shadow().setVisibility(0);
                    }
                    if (!onCreate) {
                        i = TOOLBAR_ANIMATION_DURATION;
                    }
                    animateToolbarStateBtnToState(state, i);
                    this.stateAnimator = Animators.createCrossFadeAnimator(this.hotelsListView, this.mapResultsView, 8);
                    this.stateAnimator.addListener(new C13624());
                    this.filtersControlsPresenter.switchToListControls(onCreate);
                    break;
                case STATE_MAP /*2*/:
                    if (AndroidUtils.isPhone(getContext())) {
                        getMainActivity().getToolbarManager().shadow().setVisibility(8);
                    }
                    if (!onCreate) {
                        i = TOOLBAR_ANIMATION_DURATION;
                    }
                    animateToolbarStateBtnToState(state, i);
                    this.stateAnimator = Animators.createCrossFadeAnimator(this.mapResultsView, this.hotelsListView, 8);
                    this.filtersControlsPresenter.switchToMapControls(onCreate);
                    if (hasGMS()) {
                        this.mapResultsView.onMapStateEnabled();
                        this.filtersControlsPresenter.switchToMapControls(onCreate);
                        break;
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown state");
            }
            this.resultsState = state;
            this.stateAnimator.start();
        }
    }

    private void animateToolbarStateBtnToState(int state, int animationDuration) {
        ObjectAnimator fadeOutAnimator = ObjectAnimator.ofFloat(this.toolbarStateBtn, View.ALPHA, new float[]{1.0f, 0.0f});
        fadeOutAnimator.addListener(new C13635(state));
        fadeOutAnimator.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(this.toolbarStateBtn, View.ALPHA, new float[]{0.0f, 1.0f});
        fadeInAnimator.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[STATE_MAP];
        animatorArr[0] = fadeOutAnimator;
        animatorArr[STATE_LIST] = fadeInAnimator;
        animatorSet.playSequentially(animatorArr);
        animatorSet.setDuration((long) animationDuration);
        animatorSet.start();
    }

    @Subscribe
    public void onHotelSelected(OpenHotelDetailsEvent event) {
        HotelDataSource hotelDataSource = getComponent().hotelDataSource();
        long hotelId = event.hotelData.getId();
        hotelDataSource.observeFromSearch(hotelId, this.search, (List) this.filters.getFilteredOffers().get(Long.valueOf(hotelId)), this.searchTimestamp);
        getMainActivity().showFragment(HotelFragment.create(hotelDataSource, new HotelScreenOpenInfo(getSource(), event.positionInList), event.pageIndex));
    }

    @NonNull
    private Source getSource() {
        if (this.resultsState == STATE_MAP) {
            return Source.CITY_SEARCH_MAP;
        }
        return Source.CITY_SEARCH_RESULTS;
    }

    @Subscribe
    public void onResultsScrolledToTop(ResultsScrolledToTopEvent event) {
        if (this.scrollableViewsSynchronizer != null) {
            this.scrollableViewsSynchronizer.moveBack();
        }
    }

    @Subscribe
    public void onSortingChanged(FiltersSortingChangedEvent event) {
        getMainActivity().closeBottomSheet();
        this.filtersControlsPresenter.onFiltersChanged();
    }

    @Subscribe
    public void onFiltered(FilteringFinishedEvent event) {
        this.filtersControlsPresenter.onFiltersChanged();
    }

    @Subscribe
    public void onFiltersReset(FiltersResetEvent event) {
        this.filtersControlsPresenter.onFiltersChanged();
    }

    @Subscribe
    public void onOpenFilters(OpenFiltersEvent event) {
        openFiltersFragment(event.source);
    }

    public void onMapReady(GoogleMap googleMap) {
        if (hasGMS()) {
            this.mapResultsView.onMapReady(googleMap);
        }
    }

    @Subscribe
    public void onOpenSortingMenu(OpenSortingMenuEvent event) {
        getMainActivity().showBottomSheet(new SortingPresenter(event.sortingCategory).createView(LayoutInflater.from(getContext()), null));
    }

    protected synchronized void buildGoogleApiClient() {
        this.googleApiClient = new Builder(getActivity()).addApi(LocationServices.API).build();
    }

    public void onPause() {
        super.onPause();
        if (this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }
    }

    public void onResume() {
        super.onResume();
        this.googleApiClient.connect();
    }

    @Subscribe
    public void onMapUnbreakableClusterClick(UnbreakableClusterClickEvent event) {
        showOverlay(HotelsGalleryFragment.create(event.getHotels()));
    }

    @Subscribe
    public void onOverlayClosed(OverlayClosedEvent event) {
        this.underOtherFragment = true;
        setUpToolbar();
    }

    @Subscribe
    public void onNeighbourCitiesSearchRequestEvent(NearbyCitiesSearchRequestEvent event) {
        SearchParams newParams = this.searchData.searchParams().toBuilder().destinationType(DestinationType.NEARBY_CITIES).build();
        getComponent().searchEngine().makeSearch(newParams, new SearchStartEvent(newParams, Constants.Source.NEARBY_CITIES, null));
        getMainActivity().showFragment(SearchProgressFragment.create(), false);
    }

    @Nullable
    public Object takeSnapshot() {
        return new Snapshot(this.resultsState, this.filtersControlsPresenter.saveState(), getFiltersHash(), this.mapResultsView.saveState(), this.hotelsListView.saveState(), this.underOtherFragment);
    }

    private void restoreFromSnapshot(@NonNull Snapshot snapshot) {
        this.resultsState = snapshot.state;
        this.mapResultsView.restoreState(snapshot.mapViewSavedState);
        this.hotelsListView.restoreState(snapshot.listViewSavedState);
        this.filtersControlsPresenter.restoreState(snapshot.filterControlsState);
        this.underOtherFragment = snapshot.underOverFragment;
        getView().post(SearchResultsFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$restoreFromSnapshot$0() {
        switchState(this.resultsState, true);
    }
}
