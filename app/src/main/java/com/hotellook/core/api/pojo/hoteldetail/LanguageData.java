package com.hotellook.core.api.pojo.hoteldetail;

import java.util.List;

public class LanguageData {
    private String name;
    private int percentage;
    private List<TripType> tripTypeSplit;

    public String getName() {
        return this.name;
    }

    public int getPercentage() {
        return this.percentage;
    }

    public List<TripType> getTripTypeSplit() {
        return this.tripTypeSplit;
    }
}
