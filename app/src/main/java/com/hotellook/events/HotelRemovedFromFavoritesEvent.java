package com.hotellook.events;

import com.hotellook.ui.screen.hotel.Source;

public class HotelRemovedFromFavoritesEvent {
    public final Source source;

    public HotelRemovedFromFavoritesEvent(Source source) {
        this.source = source;
    }
}
