package com.hotellook.filters.items.criterion;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class DistrictCriterion implements Criterion<HotelData> {
    private final int districtId;

    public DistrictCriterion(int districtId) {
        this.districtId = districtId;
    }

    public boolean passes(@NonNull HotelData value) {
        return value.getDistrictIds().contains(Integer.valueOf(this.districtId));
    }
}
