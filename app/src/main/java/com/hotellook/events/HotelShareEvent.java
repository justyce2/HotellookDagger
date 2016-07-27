package com.hotellook.events;

import com.hotellook.ui.screen.hotel.Source;

public class HotelShareEvent {
    public final Source source;

    public HotelShareEvent(Source source) {
        this.source = source;
    }
}
