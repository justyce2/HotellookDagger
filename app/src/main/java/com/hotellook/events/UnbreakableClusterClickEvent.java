package com.hotellook.events;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import java.util.List;

public class UnbreakableClusterClickEvent {
    private final List<HotelData> mHotels;

    public UnbreakableClusterClickEvent(List<HotelData> cluster) {
        this.mHotels = cluster;
    }

    public List<HotelData> getHotels() {
        return this.mHotels;
    }
}
