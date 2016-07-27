package com.hotellook.filters.sorting.comparator;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.utils.CompareUtils;
import java.util.Comparator;

public class RatingComparator implements Comparator<HotelData> {
    public int compare(HotelData lhs, HotelData rhs) {
        return CompareUtils.compare(lhs.getRating(), rhs.getRating());
    }
}
