package com.hotellook.filters.distancetarget;

import android.location.Location;

public interface DistanceTarget {

    public enum DestinationType {
        CENTER,
        USER,
        SIGHT,
        SEASON,
        MAP_POINT,
        SEARCH_POINT
    }

    int _hashCode();

    double distanceTo(Location location);

    boolean equals(Object obj);

    String getTitle();

    DestinationType getType();
}
