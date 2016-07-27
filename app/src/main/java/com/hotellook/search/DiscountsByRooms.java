package com.hotellook.search;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.search.Discount;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.HashMap;
import java.util.Map;

public class DiscountsByRooms {
    private final Map<Integer, Map<Integer, Discount>> discounts;

    public DiscountsByRooms(Map<Integer, Map<Integer, Discount>> discounts) {
        this.discounts = discounts;
    }

    @Nullable
    public Discount get(Offer offer) {
        Map<Integer, Discount> discountsByGate = (Map) this.discounts.get(Integer.valueOf(offer.getGateId()));
        if (discountsByGate == null) {
            return null;
        }
        return (Discount) discountsByGate.get(Integer.valueOf(offer.getRoomId()));
    }

    public DiscountsByRooms merged(DiscountsByRooms other) {
        Map<Integer, Map<Integer, Discount>> mergedDiscounts = new HashMap();
        mergedDiscounts.putAll(this.discounts);
        mergedDiscounts.putAll(other.discounts);
        return new DiscountsByRooms(mergedDiscounts);
    }
}
