package com.hotellook.booking;

import java.util.List;

public class SearchInfoForBooking {
    private final List<Integer> locations;
    private final String searchId;

    public SearchInfoForBooking(List<Integer> locations, String searchId) {
        this.locations = locations;
        this.searchId = searchId;
    }

    public List<Integer> locations() {
        return this.locations;
    }

    public String searchId() {
        return this.searchId;
    }
}
