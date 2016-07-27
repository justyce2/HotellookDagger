package com.hotellook.filters.sorting.comparator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PriceComparator implements Comparator<HotelData> {
    private final Discounts discounts;
    private final Highlights highlights;
    private final Map<Long, List<Offer>> searchResult;

    public PriceComparator(Map<Long, List<Offer>> searchResult, Discounts discounts, Highlights highlights) {
        this.searchResult = searchResult;
        this.discounts = discounts;
        this.highlights = highlights;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        Double lPrice = getMinPrice(lhs);
        Double rPrice = getMinPrice(rhs);
        if (lPrice == null && rPrice == null) {
            return 0;
        }
        if (lPrice == null) {
            return 1;
        }
        if (rPrice == null) {
            return -1;
        }
        return Double.compare(lPrice.doubleValue(), rPrice.doubleValue());
    }

    @Nullable
    private Double getMinPrice(@NonNull HotelData hotelData) {
        long id = hotelData.getId();
        List<Offer> rooms = (List) this.searchResult.get(Long.valueOf(id));
        if (CollectionUtils.isNotEmpty(rooms)) {
            return Double.valueOf(((Offer) Collections.min(rooms, CompareUtils.getComparatorByRoomPrice(this.discounts.get(id), this.highlights.get(id)))).getPrice());
        }
        return null;
    }
}
