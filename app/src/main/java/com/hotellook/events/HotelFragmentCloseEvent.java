package com.hotellook.events;

public class HotelFragmentCloseEvent {
    public final long hotelId;

    public HotelFragmentCloseEvent(long hotelId) {
        this.hotelId = hotelId;
    }
}
