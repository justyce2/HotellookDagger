package com.hotellook.filters.sorting.comparator;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HotelsComparator implements Comparator<HotelData> {
    public static final boolean ASC = true;
    public static final boolean DESC = false;
    private final boolean mAsc;
    private final Comparator<HotelData> mComparator;
    private final Map<Long, List<Offer>> mSearchResult;

    public HotelsComparator(@NonNull Map<Long, List<Offer>> searchResult, boolean asc, Comparator<HotelData> comparator) {
        this.mSearchResult = searchResult;
        this.mAsc = asc;
        this.mComparator = comparator;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        boolean hasLeftPrice = this.mSearchResult.containsKey(Long.valueOf(lhs.getId()));
        boolean hasRightPrice = this.mSearchResult.containsKey(Long.valueOf(rhs.getId()));
        if (hasLeftPrice && !hasRightPrice) {
            return -1;
        }
        if (!hasLeftPrice && hasRightPrice) {
            return 1;
        }
        if (this.mAsc) {
            return this.mComparator.compare(lhs, rhs);
        }
        return this.mComparator.compare(rhs, lhs);
    }

    public String toString() {
        return this.mComparator.getClass().getSimpleName();
    }
}
