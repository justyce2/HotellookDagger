package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.CollectionUtils;
import java.util.List;

public abstract class HotelBoolFilterItem extends BoolFilterItem<HotelData> {
    public HotelBoolFilterItem(@NonNull Criterion<HotelData> criterion) {
        super(criterion);
    }

    public HotelBoolFilterItem(@NonNull Criterion<HotelData> criterion, boolean defaultState) {
        super(criterion, defaultState);
    }

    protected int calculateCount(@NonNull SearchData searchData) {
        int counter = 0;
        List<HotelData> hotels = searchData.hotels().all();
        Criterion<HotelData> criterion = getCriterion();
        if (CollectionUtils.isNotEmpty(hotels)) {
            for (HotelData hotel : hotels) {
                if (criterion.passes(hotel)) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
