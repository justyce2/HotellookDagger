package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.AgencyFilterItem;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.HotelWebsiteAgencyFilterItem;
import com.hotellook.filters.pages.AgencyFiltersCategory;
import java.util.ArrayList;
import java.util.List;

public class AgencyFiltersPresenter implements FilterPresenter {
    private final FiltersListPresenter listPresenter;

    public AgencyFiltersPresenter(AgencyFiltersCategory agencyPage) {
        List<FilterItem<Offer>> filters = agencyPage.getFilters();
        List<FilterPresenter> presenters = new ArrayList(filters.size());
        for (FilterItem<Offer> filter : filters) {
            if (filter instanceof HotelWebsiteAgencyFilterItem) {
                presenters.add(new EmptySpacePresenter());
                presenters.add(new HotelWebsiteAgencyPresenter((HotelWebsiteAgencyFilterItem) filter));
            } else {
                presenters.add(new AgencyPresenter((AgencyFilterItem) filter));
            }
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
