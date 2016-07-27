package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.AgencyCriterion;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class AgencyFilterItem extends RoomBoolFilterItem {
    private final String agencyName;

    public AgencyFilterItem(String agencyName) {
        super(new AgencyCriterion(agencyName));
        this.agencyName = agencyName;
    }

    public String getAgencyName() {
        return this.agencyName;
    }

    @NonNull
    protected String saveTag() {
        return AgencyFilterItem.class.getSimpleName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.agencyName;
    }
}
