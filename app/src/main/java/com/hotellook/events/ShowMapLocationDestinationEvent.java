package com.hotellook.events;

import android.location.Location;

public class ShowMapLocationDestinationEvent {
    public final Location location;

    public ShowMapLocationDestinationEvent(Location location) {
        this.location = location;
    }
}
