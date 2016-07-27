package com.hotellook.events;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.mixpanel.OnScreenTimeDuration;

public class BookPredictedEvent {
    public final long hotelId;
    public final OnScreenTimeDuration onScreenTimeDuration;
    public final Offer roomData;
    public final SearchParams searchParams;

    public BookPredictedEvent(OnScreenTimeDuration onScreenTimeDuration, SearchParams searchParams, Offer roomData, long hotelId) {
        this.onScreenTimeDuration = onScreenTimeDuration;
        this.searchParams = searchParams;
        this.roomData = roomData;
        this.hotelId = hotelId;
    }
}
