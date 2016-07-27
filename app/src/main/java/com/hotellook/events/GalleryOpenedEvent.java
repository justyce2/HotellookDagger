package com.hotellook.events;

public class GalleryOpenedEvent {
    public final long hotelId;

    public GalleryOpenedEvent(long hotelId) {
        this.hotelId = hotelId;
    }
}
