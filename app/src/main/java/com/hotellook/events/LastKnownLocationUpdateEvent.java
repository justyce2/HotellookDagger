package com.hotellook.events;

import android.location.Location;
import android.support.annotation.NonNull;

public class LastKnownLocationUpdateEvent {
    private final Location location;

    public LastKnownLocationUpdateEvent(@NonNull Location location) {
        this.location = location;
    }

    @NonNull
    public Location location() {
        return this.location;
    }
}
