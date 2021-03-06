package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class HostelOrGuesthouseCriterion implements Criterion<HotelData> {
    public boolean passes(HotelData value) {
        int propertyType = value.getPropertyType();
        return propertyType == 7 || propertyType == 6;
    }
}
