package com.hotellook.ui.screen.hotel.amenity;

import android.content.Context;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;

public interface AmenityTitleDelegate {
    String title(Context context, AmenityData amenityData);
}
