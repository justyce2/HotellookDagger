package com.hotellook.events;

public class LocationSettingsRequestResultEvent {
    public final boolean requestResult;

    public LocationSettingsRequestResultEvent(boolean requestResult) {
        this.requestResult = requestResult;
    }
}
