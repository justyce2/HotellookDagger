package com.hotellook.events;

import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;

public class PoiMapFragmentOpenEvent {
    public final HotelDetailData hotelDetailData;

    public PoiMapFragmentOpenEvent(HotelDetailData hotelDetailData) {
        this.hotelDetailData = hotelDetailData;
    }
}
