package com.hotellook.ui.screen.hotel.amenity;

import android.content.Context;
import android.support.annotation.StringRes;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;

public class FreeableAmenityTitleDelegate implements AmenityTitleDelegate {
    @StringRes
    private final int def;
    @StringRes
    private final int free;

    public FreeableAmenityTitleDelegate(@StringRes int free, @StringRes int def) {
        this.free = free;
        this.def = def;
    }

    public String title(Context context, AmenityData amenity) {
        if (amenity.isFree()) {
            return context.getString(this.free);
        }
        return context.getString(this.def);
    }
}
