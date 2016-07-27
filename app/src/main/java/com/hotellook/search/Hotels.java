package com.hotellook.search;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Hotels {
    private final List<HotelData> all;
    private final Map<Integer, List<HotelData>> hotelsForLocations;

    public Hotels(Map<Integer, List<HotelData>> hotelsForLocations) {
        this.hotelsForLocations = hotelsForLocations;
        this.all = mergeHotels(hotelsForLocations);
    }

    private List<HotelData> mergeHotels(Map<Integer, List<HotelData>> hotelsForLocations) {
        List<HotelData> all = new ArrayList();
        for (List<HotelData> hotels : hotelsForLocations.values()) {
            all.addAll(hotels);
        }
        return all;
    }

    public List<HotelData> hotelsInLocation(int locationId) {
        return (List) this.hotelsForLocations.get(Integer.valueOf(locationId));
    }

    public List<HotelData> all() {
        return this.all;
    }

    public boolean isEmpty() {
        for (List<HotelData> hotelsInLocation : this.hotelsForLocations.values()) {
            if (hotelsInLocation.size() > 0) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.hotelsForLocations.keySet().toString();
    }

    public int findLocationId(long hotelId) {
        for (Integer locationId : this.hotelsForLocations.keySet()) {
            for (HotelData hotelData : (List) this.hotelsForLocations.get(locationId)) {
                if (hotelData.getId() == hotelId) {
                    return locationId.intValue();
                }
            }
        }
        throw new RuntimeException("Unable to find hotel in hotel dump.");
    }

    public HotelData findById(long hotelId) {
        for (int i = 0; i < this.all.size(); i++) {
            if (((HotelData) this.all.get(i)).getId() == hotelId) {
                return (HotelData) this.all.get(i);
            }
        }
        throw new RuntimeException("Unable to find hotel in hotel dump.");
    }

    public Map<Integer, List<HotelData>> toMap() {
        return this.hotelsForLocations;
    }

    public boolean contains(int locationId, long hotelId) {
        if (this.hotelsForLocations.keySet().contains(Integer.valueOf(locationId))) {
            for (HotelData hotelData : hotelsInLocation(locationId)) {
                if (hotelData.getId() == hotelId) {
                    return true;
                }
            }
        }
        return false;
    }
}
