package com.hotellook.filters.distancetarget;

import android.location.Location;
import com.hotellook.filters.distancetarget.DistanceTarget.DestinationType;
import com.hotellook.utils.LocationUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SeasonLocationDistanceTarget implements DistanceTarget {
    private final List<Location> locationList;
    private final String season;
    private final String title;

    public SeasonLocationDistanceTarget(List<Location> locationList, String title, String season) {
        this.locationList = locationList;
        this.title = title;
        this.season = season;
    }

    public double distanceTo(Location location) {
        List<Double> distances = new ArrayList(this.locationList.size());
        for (Location fromLocation : this.locationList) {
            distances.add(Double.valueOf(LocationUtils.simpleDistance(fromLocation, location)));
        }
        return ((Double) Collections.min(distances)).doubleValue();
    }

    public DestinationType getType() {
        return DestinationType.SEASON;
    }

    public String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.locationList.hashCode();
    }

    public String getSeason() {
        return this.season;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeasonLocationDistanceTarget that = (SeasonLocationDistanceTarget) o;
        if (this.locationList.equals(that.locationList)) {
            return this.season.equals(that.season);
        }
        return false;
    }
}
