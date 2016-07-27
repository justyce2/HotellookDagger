package com.hotellook.badges.selectors;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class RomanceSelector extends AbstractScoreSelector {
    private static final int MIN_STARS = 4;

    public RomanceSelector() {
        super(MIN_STARS);
    }

    protected int getScore(HotelData hotel) {
        return hotel.getScoring().getRomance();
    }
}
