package com.hotellook.badges.selectors;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class BusinessSelector extends AbstractScoreSelector {
    private static final int MIN_STARS = 4;

    public BusinessSelector() {
        super(MIN_STARS);
    }

    protected int getScore(HotelData hotel) {
        return hotel.getStars() >= MIN_STARS ? hotel.getScoring().getBusiness() : 0;
    }
}
