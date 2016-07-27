package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class RatingCriterion implements Criterion<HotelData> {
    private final int rating;

    public RatingCriterion(int rating) {
        this.rating = rating;
    }

    public boolean passes(HotelData value) {
        return value.getRating() >= this.rating;
    }
}
