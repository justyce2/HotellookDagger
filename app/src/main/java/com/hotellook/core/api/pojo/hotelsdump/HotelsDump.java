package com.hotellook.core.api.pojo.hotelsdump;

import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.common.Poi;
import java.util.List;
import java.util.Map;

public class HotelsDump {
    private List<District> districts;
    private List<HotelData> hotels;
    private List<Poi> pois;
    private Map<String, Map<String, SeasonDates>> seasons;

    public List<District> getDistricts() {
        return this.districts;
    }

    public List<HotelData> getHotels() {
        return this.hotels;
    }

    public List<Poi> getPois() {
        return this.pois;
    }

    public Map<String, Map<String, SeasonDates>> getSeasons() {
        return this.seasons;
    }
}
