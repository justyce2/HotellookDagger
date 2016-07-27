package com.hotellook.events;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;

public class HotelDataLoadedEvent {
    public final CityInfo cityInfo;
    public final HotelDetailData hotelDetailData;

    public HotelDataLoadedEvent(HotelDetailData hotelDetailData, CityInfo cityInfo) {
        this.hotelDetailData = hotelDetailData;
        this.cityInfo = cityInfo;
    }
}
