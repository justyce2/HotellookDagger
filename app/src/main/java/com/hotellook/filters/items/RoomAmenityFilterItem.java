package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.items.criterion.Criterion;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class RoomAmenityFilterItem extends RoomBoolFilterItem {
    public RoomAmenityFilterItem(Criterion<Offer> criterion) {
        super(criterion);
    }

    @NonNull
    protected String saveTag() {
        return RoomAmenityFilterItem.class.getSimpleName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + getCriterion().getClass().getSimpleName();
    }
}
