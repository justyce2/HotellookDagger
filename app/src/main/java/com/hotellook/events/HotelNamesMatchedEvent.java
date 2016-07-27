package com.hotellook.events;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import java.util.List;

public class HotelNamesMatchedEvent {
    public final List<HotelData> hotels;

    public HotelNamesMatchedEvent(List<HotelData> hotels) {
        this.hotels = hotels;
    }
}
