package com.hotellook.filters.distancetarget;

import android.location.Location;
import com.hotellook.db.data.PoiHistoryItem;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.utils.LocationUtils;

public class PoiHistoryDistanceTarget implements DistanceTarget {
    private final Location location;
    private final PoiHistoryItem poiHistoryItem;

    public PoiHistoryDistanceTarget(PoiHistoryItem poiHistoryItem) {
        this.poiHistoryItem = poiHistoryItem;
        this.location = LocationUtils.from(poiHistoryItem.getLocation());
    }

    public PoiHistoryItem getPoiHistoryItem() {
        return this.poiHistoryItem;
    }

    public double distanceTo(Location location) {
        return LocationUtils.simpleDistance(location, this.location);
    }

    public DestinationType getType() {
        return DestinationType.SIGHT;
    }

    public String getTitle() {
        return this.poiHistoryItem.getName();
    }

    public int hashCode() {
        return _hashCode();
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
        PoiHistoryDistanceTarget that = (PoiHistoryDistanceTarget) o;
        if (this.poiHistoryItem.equals(that.poiHistoryItem)) {
            return this.location.equals(that.location);
        }
        return false;
    }
}
