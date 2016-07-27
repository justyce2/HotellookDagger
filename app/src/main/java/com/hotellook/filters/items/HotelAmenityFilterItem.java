package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.ServiceCriterion;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class HotelAmenityFilterItem extends HotelBoolFilterItem {
    private final String service;

    public HotelAmenityFilterItem(String service) {
        super(new ServiceCriterion(service));
        this.service = service;
    }

    public String getService() {
        return this.service;
    }

    @NonNull
    protected String saveTag() {
        return HotelAmenityFilterItem.class.getSimpleName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.service;
    }
}
