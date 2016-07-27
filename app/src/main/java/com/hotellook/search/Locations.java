package com.hotellook.search;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Locations {
    private final Map<Integer, CityInfo> locations;

    public Locations(Map<Integer, CityInfo> locations) {
        this.locations = locations;
    }

    public CityInfo getById(int locationId) {
        return (CityInfo) this.locations.get(Integer.valueOf(locationId));
    }

    public List<CityInfo> list() {
        return new ArrayList(this.locations.values());
    }

    public String toString() {
        return this.locations.keySet().toString();
    }

    public int countHotels() {
        int hotelsCount = 0;
        for (CityInfo cityInfo : this.locations.values()) {
            hotelsCount += cityInfo.getHotelsCount();
        }
        return hotelsCount;
    }

    public List<Coordinates> allPins() {
        List<Coordinates> allPins = new ArrayList();
        for (CityInfo location : this.locations.values()) {
            allPins.addAll(location.getHotelPins());
        }
        return allPins;
    }

    public List<CityInfo> all() {
        return new ArrayList(this.locations.values());
    }

    public CityInfo nearestTo(Coordinates location) {
        return (CityInfo) Collections.min(this.locations.values(), Locations$$Lambda$1.lambdaFactory$(location));
    }

    public List<Integer> ids() {
        ArrayList<Integer> ids = new ArrayList();
        ids.addAll(this.locations.keySet());
        return ids;
    }
}
