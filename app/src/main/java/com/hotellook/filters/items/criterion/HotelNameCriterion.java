package com.hotellook.filters.items.criterion;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class HotelNameCriterion implements Criterion<HotelData> {
    private final String hotelName;

    public HotelNameCriterion(@NonNull String hotelName) {
        this.hotelName = hotelName.toLowerCase();
    }

    public boolean passes(HotelData value) {
        return value.getName().toLowerCase().contains(this.hotelName) || value.getLatinName().toLowerCase().contains(this.hotelName);
    }
}
