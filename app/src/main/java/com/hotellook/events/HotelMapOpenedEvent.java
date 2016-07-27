package com.hotellook.events;

public class HotelMapOpenedEvent {
    public final long hotelId;

    public HotelMapOpenedEvent(long hotelId) {
        this.hotelId = hotelId;
    }
}
