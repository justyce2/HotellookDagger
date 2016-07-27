package com.hotellook.statistics.mixpanel;

import android.content.Context;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.Offers;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResultsScreenParams implements MixPanelParamsBuilder {
    private final Discounts discounts;
    private final Highlights highlights;
    private final Offers offers;
    private final String orientation;
    private final long searchTime;

    public ResultsScreenParams(Context context, long searchTime, Offers offers, Discounts discounts, Highlights highlights) {
        this.searchTime = searchTime;
        this.orientation = AndroidUtils.isPortrait(context) ? MixPanelParams.PORTRAIT : MixPanelParams.LANDSCAPE;
        this.offers = offers;
        this.discounts = discounts;
        this.highlights = highlights;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.RESULTS_DURATION, Long.valueOf(this.searchTime / 1000));
        params.put(MixPanelParams.RESULTS_LENGTH, Integer.valueOf(this.offers.all().size()));
        params.put(MixPanelParams.ORIENTATION, this.orientation);
        params.put(MixPanelParams.HAS_RESULTS_DISCOUNT, Boolean.valueOf(hasDiscounts()));
        if (this.offers.all() != null) {
            params.put(MixPanelParams.RESULTS_LOWEST_PRICE, Double.valueOf(getLowerPrice()));
        }
        return params;
    }

    private double getLowerPrice() {
        double min = Double.MAX_VALUE;
        for (Entry<Long, List<Offer>> entry : this.offers.all().entrySet()) {
            min = Math.min(min, getBestPrice(((Long) entry.getKey()).longValue(), (List) entry.getValue()));
        }
        return min;
    }

    private double getBestPrice(long hotelId, List<Offer> prices) {
        return ((Offer) Collections.min(prices, CompareUtils.getComparatorByRoomPrice(this.discounts.get(hotelId), this.highlights.get(hotelId)))).getPriceUsd();
    }

    private boolean hasDiscounts() {
        for (Entry<Long, List<Offer>> entry : this.offers.all().entrySet()) {
            Long hotelId = (Long) entry.getKey();
            for (Offer data : (List) entry.getValue()) {
                if (OfferUtils.hasValidDiscount(data, this.discounts.get(hotelId.longValue()), this.highlights.get(hotelId.longValue()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
