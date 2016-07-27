package com.hotellook.filters.distancetarget;

import android.location.Location;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.utils.LocationUtils;

public class PoiDistanceTarget implements DistanceTarget {
    private final int cityId;
    private final Location location;
    private final Poi poi;

    public PoiDistanceTarget(int cityId, Poi poi) {
        this.cityId = cityId;
        this.poi = poi;
        this.location = LocationUtils.from(poi.getLocation());
    }

    public double distanceTo(Location location) {
        return LocationUtils.simpleDistance(location, this.location);
    }

    public DestinationType getType() {
        return DestinationType.SIGHT;
    }

    public String getTitle() {
        return this.poi.getName();
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.location.hashCode();
    }

    public Poi getPoi() {
        return this.poi;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PoiDistanceTarget that = (PoiDistanceTarget) o;
        if (this.poi.equals(that.poi)) {
            return this.location.equals(that.location);
        }
        return false;
    }

    public int cityId() {
        return this.cityId;
    }
}
