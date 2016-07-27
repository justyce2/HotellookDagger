package com.hotellook.events;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import java.util.List;

public class HotelSearchFinishEvent {
    public final List<Offer> offers;
    public final SearchParams searchParams;

    public HotelSearchFinishEvent(SearchParams searchParams, List<Offer> offers) {
        this.searchParams = searchParams;
        this.offers = offers;
    }
}
