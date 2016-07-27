package com.hotellook.filters.pages;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.filters.items.BaseLogicItem;
import com.hotellook.filters.items.DistanceFilterItem;
import com.hotellook.filters.items.FilterItem;
import com.hotellook.filters.items.HostelsAndGuesthousesFilterItem;
import com.hotellook.filters.items.HotelNameFilterItem;
import com.hotellook.filters.items.NoRoomsFilterItem;
import com.hotellook.filters.items.NoStarsFilterItem;
import com.hotellook.filters.items.PriceFilterItem;
import com.hotellook.filters.items.RatingFilterItem;
import com.hotellook.filters.items.StarsFilterItem;
import com.hotellook.filters.items.criterion.CriterionSet;
import com.hotellook.filters.items.criterion.ParallelCriterionSet;
import com.hotellook.filters.task.FilterTask;
import com.hotellook.search.SearchData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralFiltersCategory implements FiltersCategory {
    private final DistanceFilterItem distanceFilterItem;
    private final HostelsAndGuesthousesFilterItem hostelsAndGuesthousesFilterItem;
    private final HotelNameFilterItem hotelNameFilterItem;
    private final List<BaseLogicItem> items;
    private final NoRoomsFilterItem noRoomsFilterItem;
    private final NoStarsFilterItem noStarsFilterItem;
    private final PriceFilterItem priceFilterItem;
    private final RatingFilterItem ratingFilterItem;
    private final List<StarsFilterItem> starsItems;

    public GeneralFiltersCategory() {
        this.hotelNameFilterItem = new HotelNameFilterItem();
        this.distanceFilterItem = new DistanceFilterItem();
        this.noRoomsFilterItem = new NoRoomsFilterItem();
        this.hostelsAndGuesthousesFilterItem = new HostelsAndGuesthousesFilterItem();
        this.priceFilterItem = new PriceFilterItem();
        this.ratingFilterItem = new RatingFilterItem();
        this.starsItems = Arrays.asList(new StarsFilterItem[]{new StarsFilterItem(5), new StarsFilterItem(4), new StarsFilterItem(3), new StarsFilterItem(2), new StarsFilterItem(1)});
        this.noStarsFilterItem = new NoStarsFilterItem();
        this.items = new ArrayList();
        this.items.add(this.priceFilterItem);
        this.items.add(this.ratingFilterItem);
        this.items.add(this.distanceFilterItem);
        for (BaseLogicItem item : this.starsItems) {
            this.items.add(item);
        }
        this.items.add(this.noStarsFilterItem);
        this.items.add(this.hotelNameFilterItem);
        this.items.add(this.noRoomsFilterItem);
        this.items.add(this.hostelsAndGuesthousesFilterItem);
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        for (BaseLogicItem item : this.items) {
            item.setUp(searchData, persistentFilters);
        }
    }

    public void reset() {
        for (BaseLogicItem item : this.items) {
            item.reset();
        }
    }

    public void addFilterLogic(FilterTask logic) {
        if (this.priceFilterItem.inDefaultState()) {
            logic.addRoomFilter(this.noRoomsFilterItem.newCriterion());
        } else {
            logic.addRoomFilter(this.priceFilterItem.newCriterion());
        }
        logic.addHotelFilter(this.hostelsAndGuesthousesFilterItem.newCriterion());
        logic.addHotelFilter(this.hotelNameFilterItem.newCriterion());
        logic.addHotelFilter(this.distanceFilterItem.newCriterion());
        logic.addHotelFilter(this.ratingFilterItem.newCriterion());
        addStarsLogic(logic);
    }

    private void addStarsLogic(FilterTask logic) {
        CriterionSet<HotelData> criteriaSet = new ParallelCriterionSet();
        for (FilterItem<HotelData> filter : this.starsItems) {
            if (filter.enabled()) {
                criteriaSet.add(filter.newCriterion());
            }
        }
        if (this.noStarsFilterItem.enabled()) {
            criteriaSet.add(this.noStarsFilterItem.newCriterion());
        }
        if (criteriaSet.hasCriteria()) {
            logic.addHotelFilter(criteriaSet);
        }
    }

    public boolean inDefaultState() {
        for (BaseLogicItem item : this.items) {
            if (!item.inDefaultState()) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.items.hashCode();
    }

    public void saveState(FiltersSnapshot snapshot) {
        for (BaseLogicItem item : this.items) {
            item.saveState(snapshot);
        }
    }

    public void restoreState(FiltersSnapshot snapshot) {
        for (BaseLogicItem item : this.items) {
            item.restoreState(snapshot);
        }
    }

    public void release() {
        for (BaseLogicItem item : this.items) {
            item.release();
        }
    }

    public boolean isStarsInDefaultState() {
        for (StarsFilterItem item : this.starsItems) {
            if (!item.inDefaultState()) {
                return false;
            }
        }
        return true;
    }

    public boolean isPriceInDefaultState() {
        return this.priceFilterItem.inDefaultState();
    }

    public boolean isDistanceInDefaultState() {
        return this.distanceFilterItem.inDefaultState();
    }

    public boolean isNameInDefaultState() {
        return this.hotelNameFilterItem.inDefaultState();
    }

    public boolean isRatingInDefaultState() {
        return this.ratingFilterItem.inDefaultState();
    }

    public boolean isNoRoomsInDefaultState() {
        return this.noRoomsFilterItem.inDefaultState();
    }

    public boolean isHostelsAndGuesthousesInDefaultState() {
        return this.hostelsAndGuesthousesFilterItem.inDefaultState();
    }

    public DestinationType getDistanceType() {
        return this.distanceFilterItem.getDistanceType();
    }

    public int getRating() {
        return this.ratingFilterItem.getRating();
    }

    public void setRating(int rating) {
        this.ratingFilterItem.setRating(rating);
    }

    public PriceFilterItem getPriceFilterItem() {
        return this.priceFilterItem;
    }

    public NoRoomsFilterItem getNoRoomsFilterItem() {
        return this.noRoomsFilterItem;
    }

    public HostelsAndGuesthousesFilterItem getHostelsAndGuesthousesFilterItem() {
        return this.hostelsAndGuesthousesFilterItem;
    }

    public HotelNameFilterItem getHotelNameFilterItem() {
        return this.hotelNameFilterItem;
    }

    public DistanceFilterItem getDistanceFilterItem() {
        return this.distanceFilterItem;
    }

    public RatingFilterItem getRatingFilterItem() {
        return this.ratingFilterItem;
    }

    public NoStarsFilterItem getNoStarsFilterItem() {
        return this.noStarsFilterItem;
    }

    public List<StarsFilterItem> getStarsFilterItems() {
        return this.starsItems;
    }
}
