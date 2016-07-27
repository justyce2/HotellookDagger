package com.hotellook.ui.screen.filters.presenters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.hotellook.filters.items.StarsFilterItem;
import com.hotellook.filters.pages.GeneralFiltersCategory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralPresenter implements FilterPresenter {
    private final FiltersListPresenter filtersListPresenter;

    public GeneralPresenter(GeneralFiltersCategory page) {
        EmptySpacePresenter emptySpacePresenter = new EmptySpacePresenter();
        SeparatorLinePresenter separatorLinePresenter = new SeparatorLinePresenter();
        HotelNamePresenter hotelNamePresenter = new HotelNamePresenter(page.getHotelNameFilterItem());
        PriceFilterPresenter priceFilterPresenter = new PriceFilterPresenter(page.getPriceFilterItem());
        RatingFilterPresenter ratingFilterPresenter = new RatingFilterPresenter(page.getRatingFilterItem());
        DistanceFilterPresenter distanceFilterPresenter = new DistanceFilterPresenter(page.getDistanceFilterItem());
        FiltersListPresenter starsPresenter = createStarsPresenters(page);
        FilterPresenter noStarsPresenter = new NoStarsPresenter(page.getNoStarsFilterItem());
        FilterPresenter noRoomsPresenter = new NoRoomsPresenter(page.getNoRoomsFilterItem());
        FilterPresenter hostelsPresenter = new HostelsAndGuesthousesPresenter(page.getHostelsAndGuesthousesFilterItem());
        this.filtersListPresenter = new FiltersListPresenter(Arrays.asList(new FilterPresenter[]{emptySpacePresenter, priceFilterPresenter, separatorLinePresenter, ratingFilterPresenter, separatorLinePresenter, distanceFilterPresenter, emptySpacePresenter, noRoomsPresenter, hostelsPresenter, emptySpacePresenter, starsPresenter, noStarsPresenter, emptySpacePresenter, hotelNamePresenter}));
    }

    public void onFiltersUpdated() {
        this.filtersListPresenter.onFiltersUpdated();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        this.filtersListPresenter.addView(inflater, container);
    }

    private FiltersListPresenter createStarsPresenters(GeneralFiltersCategory page) {
        List<StarsFilterItem> starsFilterItems = page.getStarsFilterItems();
        List<FilterPresenter> presenters = new ArrayList(starsFilterItems.size());
        for (StarsFilterItem item : starsFilterItems) {
            presenters.add(new StarsFilterPresenter(item));
        }
        return new FiltersListPresenter(presenters);
    }
}
