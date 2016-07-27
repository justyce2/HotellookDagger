package com.hotellook.ui.screen.searchresults.map.clustering;

import com.google.android.gms.maps.model.LatLng;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.utils.LocationUtils;
import com.hotellook.utils.map.clustering.ClusterItem;

public class HotelClusterItem implements ClusterItem {
    private HotelData hotelData;

    public HotelClusterItem(HotelData hotelData) {
        this.hotelData = hotelData;
    }

    public LatLng getPosition() {
        return LocationUtils.toLatLng(this.hotelData.getLocation());
    }

    public HotelData getHotelData() {
        return this.hotelData;
    }
}
