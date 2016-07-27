package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.StarsCriterion;

public class NoStarsFilterItem extends HotelBoolFilterItem {
    public NoStarsFilterItem() {
        super(new StarsCriterion(0));
    }

    @NonNull
    protected String saveTag() {
        return NoStarsFilterItem.class.getSimpleName();
    }
}
