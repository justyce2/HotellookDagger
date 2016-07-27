package com.hotellook.statistics.ga;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.List;

public class PriceInUsd {
    public final double value;

    public PriceInUsd(SearchParams searchParams, List<Offer> prices, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.value = calculate(searchParams, prices, discounts, highlights);
    }

    public PriceInUsd(SearchParams searchParams, double price) {
        this.value = price / ((double) searchParams.nightsCount());
    }

    private double calculate(SearchParams searchParams, List<Offer> prices, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        if (searchParams == null || searchParams.checkIn() == null || searchParams.checkOut() == null) {
            return 0.0d;
        }
        return getBestPrice(prices, discounts, highlights) / ((double) searchParams.nightsCount());
    }

    private double getBestPrice(List<Offer> prices, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        if (hasPrices(prices)) {
            return getBestOffer(prices, discounts, highlights).getPriceUsd();
        }
        return 0.0d;
    }

    private boolean hasPrices(List<Offer> prices) {
        return !CollectionUtils.isEmpty(prices);
    }

    private Offer getBestOffer(List<Offer> prices, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        return (Offer) Collections.min(prices, CompareUtils.getComparatorByRoomPrice(discounts, highlights));
    }
}
