package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.StarsCriterion;
import io.fabric.sdk.android.services.events.EventsFilesManager;

public class StarsFilterItem extends HotelBoolFilterItem {
    private final int stars;

    public StarsFilterItem(int stars) {
        super(new StarsCriterion(stars));
        this.stars = stars;
    }

    public int getStars() {
        return this.stars;
    }

    @NonNull
    protected String saveTag() {
        return StarsFilterItem.class.getSimpleName() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.stars;
    }
}
