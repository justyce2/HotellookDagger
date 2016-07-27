package com.hotellook.events;

public class HotelTabSelectedEvent {
    public final long hotelId;
    public final int tabId;

    public HotelTabSelectedEvent(long hotelId, int tabId) {
        this.hotelId = hotelId;
        this.tabId = tabId;
    }
}
