package com.hotellook.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import java.util.Comparator;

public class CompareUtils {
    public static int compare(int lhs, int rhs) {
        if (lhs < rhs) {
            return -1;
        }
        return lhs == rhs ? 0 : 1;
    }

    public static int compare(long lhs, long rhs) {
        if (lhs < rhs) {
            return -1;
        }
        return lhs == rhs ? 0 : 1;
    }

    public static int compare(boolean lhs, boolean rhs) {
        if (lhs == rhs) {
            return 0;
        }
        return lhs ? 1 : -1;
    }

    public static int compare(String lhs, String rhs) {
        if (lhs == rhs) {
            return 0;
        }
        if (lhs == null) {
            return -1;
        }
        if (rhs == null) {
            return 1;
        }
        return lhs.compareTo(rhs);
    }

    @NonNull
    public static Comparator<Poi> getComparatorPoiByDistance(@NonNull Coordinates location) {
        return CompareUtils$$Lambda$1.lambdaFactory$(location);
    }

    public static int compareOffers(Offer lhs, Offer rhs, @Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        int priceCompareResult = Double.compare(lhs.getPrice(), rhs.getPrice());
        if (priceCompareResult == 0) {
            priceCompareResult = compare(rhs.getOptions().getOptionsAmount(), lhs.getOptions().getOptionsAmount());
        }
        if (priceCompareResult == 0) {
            return compareByPresenceOfDiscount(lhs, rhs, discounts, highlights);
        }
        return priceCompareResult;
    }

    private static int compareByPresenceOfDiscount(Offer lhs, Offer rhs, @Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        return compare(hasDiscount(rhs, discounts, highlights), hasDiscount(lhs, discounts, highlights));
    }

    private static boolean hasDiscount(Offer result, @Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        return OfferUtils.hasValidDiscount(result, discounts, highlights);
    }

    public static Comparator<Offer> getComparatorByRoomPrice(@Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        return CompareUtils$$Lambda$2.lambdaFactory$(discounts, highlights);
    }
}
