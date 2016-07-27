package com.hotellook.filters;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiltersSnapshot {
    public final Map<Integer, List<HotelData>> filteredHotels;
    public final Map<Long, List<Offer>> filteredOffers;
    public final int hashcode;
    private final Map<String, Object> itemsData;
    public final List<HotelData> sortedHotels;

    public FiltersSnapshot(Map<Integer, List<HotelData>> filteredHotels, Map<Long, List<Offer>> filteredOffers, List<HotelData> sortedHotels, int hashcode) {
        this.itemsData = new HashMap();
        this.filteredHotels = filteredHotels;
        this.filteredOffers = filteredOffers;
        this.sortedHotels = sortedHotels;
        this.hashcode = hashcode;
    }

    public void addData(String key, Object value) {
        this.itemsData.put(key, value);
    }

    public Object getData(String key) {
        return this.itemsData.get(key);
    }
}
