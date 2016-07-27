package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.List;
import java.util.Map;

public abstract class AbstractScoreSelector implements HotelSelector {
    private final int mMinStars;

    protected abstract int getScore(HotelData hotelData);

    protected AbstractScoreSelector(int minStars) {
        this.mMinStars = minStars;
    }

    @Nullable
    public HotelData select(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> bestResults) {
        HotelData bestHotel = null;
        double maxRate = Double.MIN_VALUE;
        for (HotelData hotel : sortedHotels) {
            double rate = calculateRate(hotel, (Offer) bestResults.get(Long.valueOf(hotel.getId())));
            if (rate > maxRate) {
                maxRate = rate;
                bestHotel = hotel;
            }
        }
        return bestHotel;
    }

    private double calculateRate(HotelData hotel, Offer offer) {
        int points = 0;
        if (hotel.getScoring() != null && hotel.getStars() >= this.mMinStars) {
            points = getScore(hotel);
        }
        return ((double) points) * (((double) hotel.getRating()) / offer.getPrice());
    }
}
