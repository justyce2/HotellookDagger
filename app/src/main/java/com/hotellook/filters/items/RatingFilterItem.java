package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.events.FiltersChangedEvent;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.RatingCriterion;
import com.hotellook.search.SearchData;
import java.util.List;

public class RatingFilterItem extends FilterItem<HotelData> {
    private static final String SAVE_TAG;
    private int count;
    private int value;

    static {
        SAVE_TAG = RatingFilterItem.class.getSimpleName();
    }

    @NonNull
    protected Criterion<HotelData> getCriterion() {
        return new RatingCriterion(this.value);
    }

    public void reset() {
        this.value = 0;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        List<HotelData> hotelDump = searchData.hotels().all();
        this.count = 0;
        for (HotelData hotelData : hotelDump) {
            if (hotelData.getRating() > 0) {
                this.count++;
            }
            if (this.count > 1) {
                break;
            }
        }
        this.value = 0;
    }

    public boolean inDefaultState() {
        return this.value == 0;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.value;
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(SAVE_TAG, Integer.valueOf(this.value));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.value = ((Integer) snapshot.getData(SAVE_TAG)).intValue();
    }

    public void release() {
    }

    public int getRating() {
        return this.value;
    }

    public void setRating(int rating) {
        this.value = rating;
        HotellookApplication.eventBus().post(new FiltersChangedEvent());
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCount() {
        return this.count;
    }

    public boolean hasValidData() {
        return this.count > 0;
    }
}
