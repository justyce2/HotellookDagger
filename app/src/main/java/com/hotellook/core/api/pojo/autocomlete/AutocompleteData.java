package com.hotellook.core.api.pojo.autocomlete;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteData {
    @Expose
    private List<AutocompleteCityData> cities;
    @Expose
    private List<AutocompleteHotelData> hotels;

    public AutocompleteData() {
        this.hotels = new ArrayList();
        this.cities = new ArrayList();
    }

    public List<AutocompleteHotelData> getHotels() {
        return this.hotels;
    }

    public List<AutocompleteCityData> getCities() {
        return this.cities;
    }
}
