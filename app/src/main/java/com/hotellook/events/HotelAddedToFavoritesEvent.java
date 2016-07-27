package com.hotellook.events;

import com.hotellook.ui.screen.hotel.Source;

public class HotelAddedToFavoritesEvent {
    public final Source source;

    public HotelAddedToFavoritesEvent(Source source) {
        this.source = source;
    }
}
