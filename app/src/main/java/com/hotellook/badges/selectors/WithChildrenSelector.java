package com.hotellook.badges.selectors;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class WithChildrenSelector extends AbstractScoreSelector {
    private static final int MIN_STARS = 3;

    public WithChildrenSelector() {
        super(MIN_STARS);
    }

    protected int getScore(HotelData hotel) {
        return hotel.getScoring().getWithChildren();
    }
}
