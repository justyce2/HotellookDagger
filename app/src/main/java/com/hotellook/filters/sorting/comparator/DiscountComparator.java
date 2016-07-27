package com.hotellook.filters.sorting.comparator;

import android.support.annotation.Nullable;
import com.hotellook.common.OfferUtils;
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

public class DiscountComparator implements Comparator<HotelData> {
    private final Discounts discounts;
    private final Highlights highlights;
    private final Comparator<HotelData> popularityComparator;
    private final Map<Long, List<Offer>> searchResult;

    public DiscountComparator(Map<Long, List<Offer>> searchResult, PopularityComparator popularityComparator, Discounts discounts, Highlights highlights) {
        this.searchResult = searchResult;
        this.popularityComparator = popularityComparator;
        this.discounts = discounts;
        this.highlights = highlights;
    }

    public int compare(HotelData lhs, HotelData rhs) {
        Offer leftOffer = getBestOffer(lhs);
        Offer rightOffer = getBestOffer(rhs);
        int compare = Float.compare(getDiscount(leftOffer, lhs.getId()), getDiscount(rightOffer, rhs.getId()));
        if (compare != 0) {
            return compare;
        }
        compare = compareBySpecialOffers(leftOffer, lhs.getId(), rightOffer, rhs.getId());
        if (compare != 0) {
            return compare;
        }
        return this.popularityComparator.compare(rhs, lhs);
    }

    private int compareBySpecialOffers(Offer leftOffer, long leftHotelId, Offer rightOffer, long rightHotelId) {
        boolean hasLeftSpecialOffer = OfferUtils.hasSpecialOffer(leftOffer, this.highlights.get(leftHotelId));
        boolean hasRightSpecialOffer = OfferUtils.hasSpecialOffer(rightOffer, this.highlights.get(rightHotelId));
        if (hasLeftSpecialOffer && hasRightSpecialOffer) {
            return CompareUtils.compareOffers(leftOffer, rightOffer, this.discounts.get(leftHotelId).merged(this.discounts.get(rightHotelId)), this.highlights.get(leftHotelId).merged(this.highlights.get(rightHotelId)));
        } else if (hasLeftSpecialOffer) {
            return -1;
        } else {
            if (hasRightSpecialOffer) {
                return 1;
            }
            return 0;
        }
    }

    private float getDiscount(@Nullable Offer offer, long hotelId) {
        return OfferUtils.hasValidDiscount(offer, this.discounts.get(hotelId), this.highlights.get(hotelId)) ? this.discounts.get(hotelId).get(offer).getChangePercentage() : 0.0f;
    }

    @Nullable
    private Offer getBestOffer(HotelData hotelData) {
        long id = hotelData.getId();
        List<Offer> prices = (List) this.searchResult.get(Long.valueOf(id));
        if (CollectionUtils.isEmpty(prices)) {
            return null;
        }
        return (Offer) Collections.min(prices, CompareUtils.getComparatorByRoomPrice(this.discounts.get(id), this.highlights.get(id)));
    }
}
