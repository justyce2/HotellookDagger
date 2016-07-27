package com.hotellook.search;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.search.Discount;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Discounts {
    private final Map<Long, Map<Integer, Map<Integer, Discount>>> discountsByRoom;

    public Discounts(Map<Long, Map<Integer, Map<Integer, Discount>>> discountsByRoom) {
        if (discountsByRoom == null) {
            discountsByRoom = Collections.emptyMap();
        }
        this.discountsByRoom = discountsByRoom;
    }

    @NonNull
    public DiscountsByRooms get(long id) {
        Map<Integer, Map<Integer, Discount>> discountsInHotel = (Map) this.discountsByRoom.get(Long.valueOf(id));
        if (discountsInHotel == null) {
            return new DiscountsByRooms(Collections.emptyMap());
        }
        return new DiscountsByRooms(discountsInHotel);
    }

    @NonNull
    public DiscountsByRooms get(Iterable<Long> ids) {
        Map<Integer, Map<Integer, Discount>> result = new HashMap();
        for (Long id : ids) {
            Map<Integer, Map<Integer, Discount>> otherDiscounts = (Map) this.discountsByRoom.get(id);
            if (otherDiscounts != null) {
                result.putAll(otherDiscounts);
            }
        }
        return new DiscountsByRooms(result);
    }
}
