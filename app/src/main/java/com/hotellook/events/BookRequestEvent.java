package com.hotellook.events;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.hotel.FrameOpenSource;

public class BookRequestEvent {
    public final String currencyCode;
    public final FrameOpenSource openSource;
    public final Offer roomData;
    public final SearchParams searchParams;

    public BookRequestEvent(SearchParams searchParams, Offer roomData, String currencyCode, FrameOpenSource openSource) {
        this.searchParams = searchParams;
        this.roomData = roomData;
        this.currencyCode = currencyCode;
        this.openSource = openSource;
    }
}
