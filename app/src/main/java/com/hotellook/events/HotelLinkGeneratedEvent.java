package com.hotellook.events;

public class HotelLinkGeneratedEvent {
    public final long hotelId;
    public final String hotelUrl;

    public HotelLinkGeneratedEvent(String hotelUrl, long hotelId) {
        this.hotelUrl = hotelUrl;
        this.hotelId = hotelId;
    }
}
