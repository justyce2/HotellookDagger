package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.criterion.HotelWebsiteAgencyCriterion;

public class HotelWebsiteAgencyFilterItem extends RoomBoolFilterItem {
    public HotelWebsiteAgencyFilterItem() {
        super(new HotelWebsiteAgencyCriterion());
    }

    @NonNull
    protected String saveTag() {
        return HotelWebsiteAgencyFilterItem.class.getSimpleName();
    }
}
