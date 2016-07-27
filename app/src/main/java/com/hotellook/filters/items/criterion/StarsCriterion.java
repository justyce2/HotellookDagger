package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class StarsCriterion implements Criterion<HotelData> {
    private final int stars;

    public StarsCriterion(int stars) {
        this.stars = stars;
    }

    public boolean passes(HotelData value) {
        return value.getStars() == this.stars;
    }
}
