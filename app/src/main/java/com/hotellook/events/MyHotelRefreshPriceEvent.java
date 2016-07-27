package com.hotellook.events;

import com.hotellook.search.SearchParams;

public class MyHotelRefreshPriceEvent {
    public final SearchParams params;

    public MyHotelRefreshPriceEvent(SearchParams params) {
        this.params = params;
    }
}
