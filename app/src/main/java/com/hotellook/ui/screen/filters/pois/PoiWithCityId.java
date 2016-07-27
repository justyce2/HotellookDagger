package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;

public class PoiWithCityId {
    public final int cityId;
    public final Poi poi;

    PoiWithCityId(Poi poi, int cityId) {
        this.poi = poi;
        this.cityId = cityId;
    }
}
