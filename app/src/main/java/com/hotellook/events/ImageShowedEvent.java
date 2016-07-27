package com.hotellook.events;

public class ImageShowedEvent {
    public final long hotelId;
    public final int position;

    public ImageShowedEvent(long hotelId, int position) {
        this.hotelId = hotelId;
        this.position = position;
    }
}
