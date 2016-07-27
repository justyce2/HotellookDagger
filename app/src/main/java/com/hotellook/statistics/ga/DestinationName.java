package com.hotellook.statistics.ga;

import com.hotellook.search.SearchParams;

public class DestinationName {
    public static final String HOTEL = "Hotel";
    public static final String LOCATION = "Location";
    public final String value;

    public DestinationName(SearchParams searchParams) {
        this.value = extract(searchParams);
    }

    private String extract(SearchParams searchParams) {
        if (searchParams.isDestinationTypeUserLocation()) {
            return LOCATION;
        }
        if (searchParams.isHotelSearch()) {
            return HOTEL;
        }
        return searchParams.destinationName();
    }
}
