package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.List;
import java.util.Map;

public class LuxSelector implements HotelSelector {
    public static final int MIN_RATING = 75;
    public static final int MIN_STARS = 4;

    @Nullable
    public HotelData select(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> bestResults) {
        HotelData best = null;
        for (HotelData data : new PriceSegmentCalculator(searchData, sortedHotels, bestResults).getHighAndMiddleSegment()) {
            if (hotelIsBetter(best, data)) {
                best = data;
            }
        }
        return best;
    }

    private boolean hotelIsBetter(HotelData best, HotelData candidate) {
        return (best == null || candidate.getRating() > best.getRating()) && candidate.getRating() >= MIN_RATING && candidate.getStars() >= MIN_STARS;
    }
}
