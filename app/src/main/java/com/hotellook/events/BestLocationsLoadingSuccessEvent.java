package com.hotellook.events;

import com.hotellook.core.api.pojo.geo.GeoLocationData;
import java.util.List;

public class BestLocationsLoadingSuccessEvent {
    public final List<GeoLocationData> locations;

    public BestLocationsLoadingSuccessEvent(List<GeoLocationData> locations) {
        this.locations = locations;
    }
}
