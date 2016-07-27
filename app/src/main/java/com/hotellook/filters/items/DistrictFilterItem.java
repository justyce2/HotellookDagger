package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.DistrictCriterion;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class DistrictFilterItem extends HotelBoolFilterItem {
    private final String districtName;

    public DistrictFilterItem(String districtName, int districtId) {
        super(new DistrictCriterion(districtId));
        this.districtName = districtName;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    @NonNull
    protected String saveTag() {
        return DistrictFilterItem.class.getSimpleName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.districtName;
    }
}
