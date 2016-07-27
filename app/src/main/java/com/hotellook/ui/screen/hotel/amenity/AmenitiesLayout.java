package com.hotellook.ui.screen.hotel.amenity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;
import java.util.List;

public class AmenitiesLayout extends AmenitiesTwoColumnLayout {
    public AmenitiesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(@NonNull List<AmenityData> amenities) {
        addViews(new AmenityViewsFactory().createViews(this, amenities));
    }
}
