package com.hotellook.search;

import com.hotellook.core.api.pojo.hotelsdump.SeasonDates;
import java.util.Map;

public class Seasons {
    private final Map<Integer, Map<String, Map<String, SeasonDates>>> seasonsForLocations;

    public Seasons(Map<Integer, Map<String, Map<String, SeasonDates>>> seasonsForLocations) {
        this.seasonsForLocations = seasonsForLocations;
    }

    public Map<String, Map<String, SeasonDates>> inLocation(int locationId) {
        return (Map) this.seasonsForLocations.get(Integer.valueOf(locationId));
    }

    public Map<String, Map<String, SeasonDates>> anyCity() {
        return (Map) this.seasonsForLocations.values().iterator().next();
    }
}
