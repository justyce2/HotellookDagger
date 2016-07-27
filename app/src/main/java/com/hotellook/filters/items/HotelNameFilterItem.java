package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.HotelNameCriterion;
import com.hotellook.search.SearchData;

public class HotelNameFilterItem extends FilterItem<HotelData> {
    private static final String DEFAULT_HOTEL_NAME = "";
    private static final String SAVE_TAG;
    private String hotelName;

    public HotelNameFilterItem() {
        this.hotelName = DEFAULT_HOTEL_NAME;
    }

    static {
        SAVE_TAG = HotelNameFilterItem.class.getSimpleName();
    }

    @NonNull
    protected Criterion<HotelData> getCriterion() {
        return new HotelNameCriterion(this.hotelName);
    }

    public void reset() {
        this.hotelName = DEFAULT_HOTEL_NAME;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.hotelName = DEFAULT_HOTEL_NAME;
    }

    public boolean inDefaultState() {
        return DEFAULT_HOTEL_NAME.equals(this.hotelName);
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.hotelName.hashCode();
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(SAVE_TAG, this.hotelName);
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.hotelName = (String) snapshot.getData(SAVE_TAG);
    }

    public void release() {
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}
