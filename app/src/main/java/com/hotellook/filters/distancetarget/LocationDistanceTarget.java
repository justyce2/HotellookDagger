package com.hotellook.filters.distancetarget;

import android.location.Location;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.utils.LocationUtils;

public class LocationDistanceTarget implements DistanceTarget {
    public final Location location;
    public final String title;
    public final DestinationType type;

    public LocationDistanceTarget(Location location, String title, DestinationType type) {
        this.location = location;
        this.title = title;
        this.type = type;
    }

    public double distanceTo(Location location) {
        return LocationUtils.simpleDistance(location, this.location);
    }

    public DestinationType getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }

    public int _hashCode() {
        return this.location.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationDistanceTarget that = (LocationDistanceTarget) o;
        if (!this.location.equals(that.location)) {
            return false;
        }
        if (this.type != that.type) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return _hashCode();
    }
}
