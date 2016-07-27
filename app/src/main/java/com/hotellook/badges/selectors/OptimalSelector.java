package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.List;
import java.util.Map;

public class OptimalSelector implements HotelSelector {
    @Nullable
    public HotelData select(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> bestResults) {
        return new PopularSelector().select(searchData, new PriceSegmentCalculator(searchData, sortedHotels, bestResults).getMiddleSegment(), bestResults);
    }
}
