package com.hotellook.events;

public class OnStreetViewCheckLoadedEvent {
    private final boolean hasStreetView;

    public OnStreetViewCheckLoadedEvent(boolean hasStreetView) {
        this.hasStreetView = hasStreetView;
    }
}
