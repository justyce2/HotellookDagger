package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.items.DistrictFilterItem;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.pages.DistrictsFiltersCategory;
import java.util.ArrayList;
import java.util.List;

public class DistrictsFiltersPresenter implements FilterPresenter {
    private final FiltersListPresenter listPresenter;

    public DistrictsFiltersPresenter(DistrictsFiltersCategory districtsPage) {
        List<FilterItem<HotelData>> filters = districtsPage.getFilters();
        List<FilterPresenter> presenters = new ArrayList(filters.size());
        for (FilterItem<HotelData> filter : filters) {
            presenters.add(new DistrictPresenter((DistrictFilterItem) filter));
        }
        this.listPresenter = new FiltersListPresenter(presenters);
    }

    public void onFiltersUpdated() {
        this.listPresenter.onFiltersUpdated();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        new EmptySpacePresenter().addView(inflater, container);
        this.listPresenter.addView(inflater, container);
    }
}
