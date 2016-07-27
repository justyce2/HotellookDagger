package com.hotellook.ui.screen.filters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.common.view.MonkeySafeClickListener;
import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.events.FilteringFinishedEvent;
import com.hotellook.events.FiltersApplyEvent;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.events.FiltersResetEvent;
import com.hotellook.events.OpenHotelNameSelectorEvent;
import com.hotellook.filters.Filters;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.search.Search;
import com.hotellook.ui.activity.MainActivity;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment;
import com.hotellook.ui.screen.filters.presenters.AgencyFiltersPresenter;
import com.hotellook.ui.screen.filters.presenters.DistrictsFiltersPresenter;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import com.hotellook.ui.screen.filters.presenters.GeneralPresenter;
import com.hotellook.ui.screen.filters.presenters.OptionsAndServicesPresenter;
import com.hotellook.ui.screen.gallery.ViewSizeCalculator;
import com.hotellook.ui.screen.searchresults.ViewHorizontalAligner;
import com.hotellook.ui.toolbar.ToolbarSettings;
import com.hotellook.ui.view.appbar.NestedParentRelativeLayout;
import com.hotellook.ui.view.appbar.ToolbarScrollingBehavior;
import com.hotellook.ui.view.tabview.SlidingTabLayout;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.StringUtils;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class FilterFragment extends BaseFragment {
    public static final String TOOLBAR_POSITION_KEY = "TOOLBAR_POSITION_KEY";
    private FilterOpenSource filterOpenSource;
    private Filters filters;
    private FiltersSnapshot filtersStateOnOpen;
    private TextView foundHotelsText;
    private boolean isToolbarCollapsed;
    private NestedParentRelativeLayout layout;
    private View resetBtn;
    private Search search;
    private SlidingTabLayout slidingTabLayout;
    private View tabsShadow;
    private ToolbarScrollingBehavior toolbarScrollingBehavior;
    private ViewPager viewPager;

    /* renamed from: com.hotellook.ui.screen.filters.FilterFragment.1 */
    class C12561 extends MonkeySafeClickListener {
        C12561() {
        }

        public void onSafeClick(View v) {
            FilterFragment.this.postApplyFiltersEvent();
            FilterFragment.this.clearFiltersStateOnOpen();
            FilterFragment.this.getMainActivity().onBackPressed();
        }
    }

    /* renamed from: com.hotellook.ui.screen.filters.FilterFragment.2 */
    class C12572 extends MonkeySafeClickListener {
        C12572() {
        }

        public void onSafeClick(View v) {
            FilterFragment.this.filters.reset();
            FilterFragment.this.postApplyFiltersEvent();
        }
    }

    public static FilterFragment create(FilterOpenSource source) {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setFilterOpenSource(source);
        return filterFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.isToolbarCollapsed = savedInstanceState.getBoolean(TOOLBAR_POSITION_KEY, false);
        }
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.filters = this.search.filters();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_filters, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.layout = (NestedParentRelativeLayout) view;
        getMainActivity().lockDrawer();
        setUpToolbar();
        new ViewHorizontalAligner(getContext(), new ViewSizeCalculator(getContext()).calculateControlsWidth()).alignWithPaddings(this.layout.findViewById(C1178R.id.controls_container));
        setUpPager(view);
        setUpApplyButton(view);
        initHotelsCountView();
        AndroidUtils.addMarginToPlaceViewBelowToolbar(this.viewPager);
        int toolbarHeight = AndroidUtils.getToolbarHeight(getContext());
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.viewPager.getLayoutParams();
        marginLayoutParams.bottomMargin -= toolbarHeight;
        ((MarginLayoutParams) this.slidingTabLayout.getLayoutParams()).topMargin = toolbarHeight;
        AndroidUtils.addMarginToPlaceViewBelowStatusBar(view);
        AndroidUtils.addPaddingToOffsetNavBarBottom(view);
        HotellookApplication.eventBus().register(this);
        setUpAppBarScrolling();
    }

    private void setUpPager(View view) {
        this.slidingTabLayout = (SlidingTabLayout) view.findViewById(C1178R.id.tabs);
        this.slidingTabLayout.setCustomTabView(C1178R.layout.view_filters_tab_item, C1178R.id.tv);
        this.slidingTabLayout.setDistributeEvenly(true);
        this.tabsShadow = view.findViewById(C1178R.id.tabs_shadow);
        this.viewPager = (ViewPager) view.findViewById(C1178R.id.pager);
        if (hasInitialSnapshot()) {
            restoreFromSnapshot((FiltersSnapshot) initialSnapshot());
        } else {
            this.filtersStateOnOpen = this.filters.createSnapshot();
        }
        this.viewPager.setAdapter(new FiltersAdapter(getActivity(), getChildFragmentManager(), getAdapterPresentersWithTitles(), this.viewPager.getId()));
        this.viewPager.setOffscreenPageLimit(4);
        this.viewPager.setPageMargin(getStandardOffset());
        this.slidingTabLayout.setViewPager(this.viewPager);
    }

    private List<Pair<FilterPresenter, String>> getAdapterPresentersWithTitles() {
        List<Pair<FilterPresenter, String>> presenters = new ArrayList();
        presenters.add(new Pair(new GeneralPresenter(this.filters.getGeneralPage()), getString(C1178R.string.filters_tab_general)));
        presenters.add(new Pair(new OptionsAndServicesPresenter(this.filters.getOptionsAndServicesPage()), getString(C1178R.string.filters_tab_options)));
        if (hasMoreThanOneDistrict()) {
            presenters.add(new Pair(new DistrictsFiltersPresenter(this.filters.getDistrictsPage()), getString(C1178R.string.filters_tab_districts)));
        }
        presenters.add(new Pair(new AgencyFiltersPresenter(this.filters.getAgencyPage()), getString(C1178R.string.filters_tab_agencies)));
        return presenters;
    }

    private boolean hasMoreThanOneDistrict() {
        List<District> districts = this.search.searchData().districts().all();
        if (!CollectionUtils.isNotEmpty(districts) || districts.size() <= 1) {
            return false;
        }
        return true;
    }

    private void setUpApplyButton(View view) {
        view.findViewById(C1178R.id.btn_apply).setOnClickListener(new C12561());
    }

    private void postApplyFiltersEvent() {
        HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, this.filterOpenSource, FilterApplySource.GENERAL));
    }

    public void setUpAppBarScrolling() {
        this.toolbarScrollingBehavior = new ToolbarScrollingBehavior(getToolbar(), this.layout);
        this.toolbarScrollingBehavior.setMovingViews(this.viewPager, this.layout.findViewById(C1178R.id.tabs_container), this.tabsShadow);
        if (this.isToolbarCollapsed) {
            this.toolbarScrollingBehavior.collapse();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TOOLBAR_POSITION_KEY, this.toolbarScrollingBehavior.isCollapsed());
        super.onSaveInstanceState(outState);
    }

    public void onDestroyView() {
        getMainActivity().unlockDrawer();
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
        if (needToRestoreFiltersState()) {
            this.filters.restoreState(this.filtersStateOnOpen);
        }
        this.isToolbarCollapsed = this.toolbarScrollingBehavior.isCollapsed();
        this.toolbarScrollingBehavior.clean();
    }

    private boolean needToRestoreFiltersState() {
        return this.filtersStateOnOpen != null;
    }

    @Subscribe
    public void onFiltersChanged(FiltersChangedEvent event) {
        this.resetBtn.setEnabled(!this.filters.inInittedState());
    }

    @Subscribe
    public void onFilteringFinished(FilteringFinishedEvent event) {
        initHotelsCountView();
    }

    @Subscribe
    public void onFiltersReset(FiltersResetEvent event) {
        this.resetBtn.setEnabled(!this.filters.inInittedState());
        initHotelsCountView();
        this.filtersStateOnOpen = this.filters.createSnapshot();
    }

    @Subscribe
    public void onOpenFilterHotelName(OpenHotelNameSelectorEvent event) {
        getMainActivity().showOverlay(HotelNameSelectorFragment.create(event.filter, getAllHotels()));
    }

    private List<HotelData> getAllHotels() {
        return this.search.searchData().hotels().all();
    }

    private void initHotelsCountView() {
        String text;
        int allHotelsCount = getAllHotels().size();
        int filteredCount = this.filters.getFilteredHotelsSize();
        String allHotelsCountString = StringUtils.toStringWithDelimiter(allHotelsCount);
        String filteredCountString = StringUtils.toStringWithDelimiter(filteredCount);
        if (filteredCount == 0) {
            text = getResources().getString(C1178R.string.no_hotels);
        } else {
            text = getResources().getQuantityString(C1178R.plurals.filters_hotels, filteredCount, new Object[]{filteredCountString, allHotelsCountString});
        }
        this.foundHotelsText.setText(text);
    }

    private void setUpToolbar() {
        boolean z;
        View toolbarLayout = LayoutInflater.from(getActivity()).inflate(C1178R.layout.toolbar_filter, getMainActivity().getToolbarManager().getToolbar(), false);
        this.foundHotelsText = (TextView) toolbarLayout.findViewById(C1178R.id.counter);
        this.resetBtn = toolbarLayout.findViewById(C1178R.id.reset_btn);
        View view = this.resetBtn;
        if (this.filters.inInittedState()) {
            z = false;
        } else {
            z = true;
        }
        view.setEnabled(z);
        this.resetBtn.setOnClickListener(new C12572());
        MainActivity activity = getMainActivity();
        activity.getToolbarManager().setUpToolbar(activity.getSupportActionBar(), new ToolbarSettings().navigationMode(2).bkgColor(ContextCompat.getColor(getContext(), C1178R.color.toolbar_green_bkg)).statusBarColor(ContextCompat.getColor(getContext(), C1178R.color.spf_statusbar_bkg)).toggleColor(ContextCompat.getColor(getContext(), 17170443)).withCustomView(toolbarLayout));
    }

    public void setFilterOpenSource(FilterOpenSource filterOpenSource) {
        this.filterOpenSource = filterOpenSource;
    }

    @Nullable
    public Object takeSnapshot() {
        FiltersSnapshot filtersStateOnOpen = this.filtersStateOnOpen;
        clearFiltersStateOnOpen();
        return filtersStateOnOpen;
    }

    private void clearFiltersStateOnOpen() {
        this.filtersStateOnOpen = null;
    }

    private void restoreFromSnapshot(@NonNull FiltersSnapshot snapshot) {
        this.filtersStateOnOpen = snapshot;
    }
}
