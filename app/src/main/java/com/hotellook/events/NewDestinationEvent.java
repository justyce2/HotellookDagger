package com.hotellook.events;

public class NewDestinationEvent {
    public final long hotelId;
    public final String language;
    public final int locationId;
    public final int type;

    public NewDestinationEvent(int destinationId, long hotelId, int type, String language) {
        this.locationId = destinationId;
        this.type = type;
        this.hotelId = hotelId;
        this.language = language;
    }
}
