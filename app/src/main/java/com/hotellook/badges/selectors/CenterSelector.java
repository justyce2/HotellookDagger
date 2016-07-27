package com.hotellook.badges.selectors;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchData;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CenterSelector implements HotelSelector {
    public static final int NEAREST_TO_CENTER_COUNT = 20;

    /* renamed from: com.hotellook.badges.selectors.CenterSelector.1 */
    class C11911 implements Comparator<HotelData> {
        C11911() {
        }

        public int compare(HotelData lhs, HotelData rhs) {
            return Double.compare(lhs.getDistance(), rhs.getDistance());
        }
    }

    @Nullable
    public HotelData select(SearchData searchData, List<HotelData> sortedHotels, Map<Long, Offer> bestResults) {
        List<HotelData> highAndMiddleSegment = new PriceSegmentCalculator(searchData, sortedHotels, bestResults).getHighAndMiddleSegment();
        Collections.sort(highAndMiddleSegment, new C11911());
        int size = Math.min(NEAREST_TO_CENTER_COUNT, highAndMiddleSegment.size());
        double maxRpp = Double.MIN_VALUE;
        HotelData resultHotel = null;
        for (int i = 0; i < size; i++) {
            HotelData hotelData = (HotelData) highAndMiddleSegment.get(i);
            double rpp = ((double) hotelData.getRating()) / ((Offer) bestResults.get(Long.valueOf(hotelData.getId()))).getPrice();
            if (rpp > maxRpp) {
                maxRpp = rpp;
                resultHotel = hotelData;
            }
        }
        return resultHotel;
    }
}
