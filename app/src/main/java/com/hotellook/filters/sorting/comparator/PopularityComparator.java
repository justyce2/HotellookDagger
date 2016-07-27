package com.hotellook.filters.sorting.comparator;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.utils.CompareUtils;
import java.util.Comparator;

public class PopularityComparator implements Comparator<HotelData> {
    private final BadgesComparator badgesComparator;

    public PopularityComparator(@NonNull BadgesComparator badgesComparator) {
        this.badgesComparator = badgesComparator;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        int comparisonResult = this.badgesComparator.compare(lhs, rhs);
        if (comparisonResult == 0) {
            return CompareUtils.compare(lhs.getPopularity(), rhs.getPopularity());
        }
        return comparisonResult;
    }
}
