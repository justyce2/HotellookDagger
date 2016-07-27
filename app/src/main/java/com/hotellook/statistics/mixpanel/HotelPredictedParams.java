package com.hotellook.statistics.mixpanel;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.MixPanelParams;
import java.util.HashMap;
import java.util.Map;

public class HotelPredictedParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final long durationInSeconds;
    private long hotelId;
    private final int predictedCount;
    private final Offer roomData;
    private final SearchParams searchParams;

    public HotelPredictedParams(int predictedCount, long durationInSeconds, long hotelId, Offer roomData, SearchParams searchParams) {
        this.predictedCount = predictedCount;
        this.durationInSeconds = durationInSeconds;
        this.hotelId = hotelId;
        this.roomData = roomData;
        this.searchParams = searchParams;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.PREDICTED_HOTEL_ID, Long.valueOf(this.hotelId));
        params.put(MixPanelParams.PREDICTED_GATE_ID, Integer.valueOf(this.roomData.getGateId()));
        params.put(MixPanelParams.PREDICTED_ROOM_ID, Integer.valueOf(this.roomData.getRoomId()));
        int nightsCount = this.searchParams.nightsCount();
        double priceUsd = this.roomData.getPriceUsd();
        params.put(MixPanelParams.PREDICTED_LENGTH, Integer.valueOf(nightsCount));
        params.put(MixPanelParams.PREDICTED_OFFER_PRICE, Double.valueOf(priceUsd));
        params.put(MixPanelParams.PREDICTED_DAY_PRICE, Double.valueOf(priceUsd / ((double) nightsCount)));
        params.put(MixPanelParams.PREDICTED_COUNT, Integer.valueOf(this.predictedCount));
        params.put(MixPanelParams.PREDICTED_DURATION, Long.valueOf(this.durationInSeconds));
        return params;
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.PREDICTED_COUNT, Integer.valueOf(this.predictedCount));
        return params;
    }
}
