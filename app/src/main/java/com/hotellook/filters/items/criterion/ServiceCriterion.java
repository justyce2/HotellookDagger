package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.hotelsdump.HotelData;

public class ServiceCriterion implements Criterion<HotelData> {
    private final String service;

    public ServiceCriterion(String service) {
        this.service = service;
    }

    public boolean passes(HotelData value) {
        for (String item : value.getAmenitiesShort().values()) {
            if (this.service.equals(item)) {
                return true;
            }
        }
        return false;
    }
}
