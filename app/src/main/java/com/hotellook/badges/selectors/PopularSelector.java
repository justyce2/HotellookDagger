package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.List;
import java.util.Map;

public class PopularSelector implements HotelSelector {
    public static final int MIN_ORDERS_COUNT = 10;

    @Nullable
    public HotelData select(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> map) {
        HotelData result = null;
        for (HotelData hotelData : sortedHotels) {
            if (hotelIsBetter(result, hotelData)) {
                result = hotelData;
            }
        }
        return result;
    }

    private boolean hotelIsBetter(HotelData best, HotelData candidate) {
        return (best == null || candidate.getOrdersCount() > best.getOrdersCount()) && candidate.getOrdersCount() > MIN_ORDERS_COUNT;
    }
}
