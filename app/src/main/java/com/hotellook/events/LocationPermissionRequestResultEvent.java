package com.hotellook.events;

public class LocationPermissionRequestResultEvent {
    public final boolean requestResult;

    public LocationPermissionRequestResultEvent(boolean requestResult) {
        this.requestResult = requestResult;
    }
}
