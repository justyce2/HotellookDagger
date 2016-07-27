package com.hotellook.utils;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class CompareUtils$$Lambda$2 implements Comparator {
    private final DiscountsByRooms arg$1;
    private final HighlightsByRooms arg$2;

    private CompareUtils$$Lambda$2(DiscountsByRooms discountsByRooms, HighlightsByRooms highlightsByRooms) {
        this.arg$1 = discountsByRooms;
        this.arg$2 = highlightsByRooms;
    }

    public static Comparator lambdaFactory$(DiscountsByRooms discountsByRooms, HighlightsByRooms highlightsByRooms) {
        return new CompareUtils$$Lambda$2(discountsByRooms, highlightsByRooms);
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return CompareUtils.compareOffers((Offer) obj, (Offer) obj2, this.arg$1, this.arg$2);
    }
}
