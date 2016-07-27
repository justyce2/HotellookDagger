package com.hotellook.common;

import android.support.annotation.Nullable;
import com.hotellook.core.api.Constants.PriceHighlightType;
import com.hotellook.core.api.pojo.search.Discount;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.statistics.Constants.MixPanelParams;

public class OfferUtils {
    private static final float MAX_DISCOUNT_PERCENT = 75.0f;
    private static final float MIN_DISCOUNT_PERCENT = 3.0f;

    public static boolean hasValidDiscount(@Nullable Offer offer, @Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        if (offer == null || discounts == null || highlights == null) {
            return false;
        }
        Discount discount = discounts.get(offer);
        if (discount == null) {
            return false;
        }
        if (!MixPanelParams.DISCOUNT.equals(highlights.highlight(offer))) {
            return false;
        }
        float percentage = Math.abs(discount.getChangePercentage());
        if (percentage < MIN_DISCOUNT_PERCENT || percentage > MAX_DISCOUNT_PERCENT) {
            return false;
        }
        return true;
    }

    public static boolean hasSpecialOffer(Offer data, @Nullable HighlightsByRooms highlightsByRooms) {
        if (data == null || highlightsByRooms == null) {
            return false;
        }
        String highlight = highlightsByRooms.highlight(data);
        if (PriceHighlightType.PRIVATE.equals(highlight) || PriceHighlightType.MOBILE.equals(highlight)) {
            return true;
        }
        return false;
    }

    public static boolean hasPrivatePrice(Offer offer) {
        return offer.getOptions() != null && offer.getOptions().hasPrivatePrice();
    }
}
