package com.hotellook.ui.screen.filters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hotellook.events.OpenDistanceSelectorEvent;
import com.hotellook.events.OpenHotelNameSelectorEvent;
import com.hotellook.filters.Filters;
import com.hotellook.search.Search;
import com.hotellook.ui.screen.common.BaseFragment;
import com.hotellook.ui.screen.filters.hotelname.HotelNameSelectorFragment;
import com.hotellook.ui.screen.filters.presenters.AgencyFiltersPresenter;
import com.hotellook.ui.screen.filters.presenters.DistrictsFiltersPresenter;
import com.hotellook.ui.screen.filters.presenters.FilterPresenter;
import com.hotellook.ui.screen.filters.presenters.GeneralPresenter;
import com.hotellook.ui.screen.filters.presenters.OptionsAndServicesPresenter;
import com.hotellook.ui.view.tabview.SlidingTabLayout;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.StringUtils;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class FilterFragmentSideMenu extends BaseFragment {
    private FilterOpenSource filterOpenSource;
    private Filters filters;
    @Nullable
    private TextView foundHotelsText;
    @Nullable
    private View resetBtn;
    private Search search;

    /* renamed from: com.hotellook.ui.screen.filters.FilterFragmentSideMenu.1 */
    class C12581 extends MonkeySafeClickListener {
        C12581() {
        }

        public void onSafeClick(View v) {
            FilterFragmentSideMenu.this.filters.reset();
            FilterFragmentSideMenu.this.postApplyFiltersEvent();
        }
    }

    public FilterFragmentSideMenu() {
        this.filterOpenSource = FilterOpenSource.LIST;
    }

    public static FilterFragmentSideMenu create(FilterOpenSource source) {
        FilterFragmentSideMenu filterFragment = new FilterFragmentSideMenu();
        filterFragment.setFilterOpenSource(source);
        return filterFragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.search = getComponent().searchKeeper().lastSearchOrThrowException();
        this.filters = this.search.filters();
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(C1178R.layout.fragment_filter_side, container, false);
    }

    @Subscribe
    public void onFiltersChanged(FiltersChangedEvent event) {
        if (this.resetBtn != null) {
            this.resetBtn.setEnabled(!this.filters.inInittedState());
        }
    }

    @Subscribe
    public void onFilteringFinished(FilteringFinishedEvent event) {
        initHotelsCountView();
    }

    @Subscribe
    public void onFiltersReset(FiltersResetEvent event) {
        if (this.resetBtn != null) {
            this.resetBtn.setEnabled(!this.filters.inInittedState());
        }
        initHotelsCountView();
    }

    @Subscribe
    public void onOpenFilterHotelName(OpenHotelNameSelectorEvent event) {
        getMainActivity().closeRightDrawer();
        getMainActivity().addFragment(HotelNameSelectorFragment.create(event.filter, getAllHotels()));
    }

    @Subscribe
    public void onOpenPoiSelector(OpenDistanceSelectorEvent event) {
        getMainActivity().closeRightDrawer();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (AndroidUtils.isPortrait(getContext())) {
            View pseudoToolbar = view.findViewById(C1178R.id.toolbar_layout);
            AndroidUtils.addPaddingToOffsetStatusBar(pseudoToolbar);
            setUpToolbar(pseudoToolbar);
        } else {
            AndroidUtils.addPaddingToOffsetToolbar(view);
            view.post(FilterFragmentSideMenu$$Lambda$1.lambdaFactory$(this));
        }
        setUpPager(view);
        HotellookApplication.eventBus().register(this);
    }

    /* synthetic */ void lambda$onViewCreated$0() {
        if (getMainActivity() != null) {
            Toolbar toolbar = getMainActivity().getToolbarManager().getToolbar();
            if (toolbar != null) {
                setUpToolbar(toolbar);
            }
        }
    }

    static /* synthetic */ void lambda$setUpToolbar$1(View v) {
    }

    public void setUpToolbar(View view) {
        view.setOnClickListener(FilterFragmentSideMenu$$Lambda$2.lambdaFactory$());
        this.foundHotelsText = (TextView) view.findViewById(C1178R.id.counter);
        initHotelsCountView();
        setUpResetBtn(view);
    }

    public void onDestroyView() {
        HotellookApplication.eventBus().unregister(this);
        super.onDestroyView();
    }

    private void setUpResetBtn(View view) {
        this.resetBtn = view.findViewById(C1178R.id.reset_btn);
        if (this.resetBtn != null) {
            this.resetBtn.setEnabled(!this.filters.inInittedState());
            this.resetBtn.setOnClickListener(new C12581());
        }
    }

    private void setUpPager(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(C1178R.id.pager);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(C1178R.id.tabs);
        slidingTabLayout.setCustomTabView(C1178R.layout.view_filters_tab_item, C1178R.id.tv);
        slidingTabLayout.setDistributeEvenly(true);
        viewPager.setAdapter(new FiltersAdapter(getActivity(), getChildFragmentManager(), getAdapterPresentersWithTitles(), viewPager.getId()));
        viewPager.setOffscreenPageLimit(4);
        slidingTabLayout.setViewPager(viewPager);
    }

    private void postApplyFiltersEvent() {
        HotellookApplication.eventBus().post(new FiltersApplyEvent(this.filters, this.filterOpenSource, FilterApplySource.GENERAL));
    }

    public void setFilterOpenSource(FilterOpenSource filterOpenSource) {
        this.filterOpenSource = filterOpenSource;
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

    private void initHotelsCountView() {
        if (this.foundHotelsText != null) {
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
    }

    private List<HotelData> getAllHotels() {
        return this.search.searchData().hotels().all();
    }
}
