package com.hotellook.interactor.favorites;

import com.hotellook.db.data.FavoriteHotelDataEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FavoritesParsedData {
    private final List<FavoriteCityData> cities;
    private final Map<Integer, List<FavoriteHotelDataEntity>> hotels;

    public FavoritesParsedData(List<FavoriteCityData> cities, Map<Integer, List<FavoriteHotelDataEntity>> hotels) {
        this.cities = cities;
        this.hotels = hotels;
    }

    public List<FavoriteCityData> cities() {
        return this.cities;
    }

    public List<FavoriteHotelDataEntity> hotels(int id) {
        return (List) this.hotels.get(Integer.valueOf(id));
    }

    public List<FavoriteHotelDataEntity> hotels() {
        List<FavoriteHotelDataEntity> results = new ArrayList();
        for (List<FavoriteHotelDataEntity> hotelsInCity : this.hotels.values()) {
            results.addAll(hotelsInCity);
        }
        return results;
    }
}
