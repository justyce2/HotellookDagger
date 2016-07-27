package com.hotellook.ui.screen.hotel.amenity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AmenityViewsFactory {
    private Map<String, Integer> icons;
    private Map<String, AmenityTitleDelegate> titleDelegates;

    public AmenityViewsFactory() {
        this.icons = new HashMap();
        this.titleDelegates = new HashMap();
        this.icons.put(AmenityData.AMENITY_PARKING, Integer.valueOf(C1178R.drawable.ic_amenity_parking));
        this.icons.put(AmenityData.AMENITY_24_DESK, Integer.valueOf(C1178R.drawable.ic_amenity_24hours));
        this.icons.put(AmenityData.AMENITY_LOW_MOBILITY, Integer.valueOf(C1178R.drawable.ic_amenity_low_mobility));
        this.icons.put(AmenityData.AMENITY_RESTAURANT, Integer.valueOf(C1178R.drawable.ic_amenity_restaurant));
        this.icons.put(AmenityData.AMENITY_BAR, Integer.valueOf(C1178R.drawable.ic_amenity_bar));
        this.icons.put(AmenityData.AMENITY_BUSINESS_CENTRE, Integer.valueOf(C1178R.drawable.ic_amenity_business_center));
        this.icons.put(AmenityData.AMENITY_LAUNDRY, Integer.valueOf(C1178R.drawable.ic_amenity_laundry));
        this.icons.put(AmenityData.AMENITY_CONCIERGE, Integer.valueOf(C1178R.drawable.ic_amenity_concierge));
        this.icons.put(AmenityData.AMENITY_WIFI_IN_PUBLIC, Integer.valueOf(C1178R.drawable.ic_amenity_wifi));
        this.icons.put(AmenityData.AMENITY_GYM, Integer.valueOf(C1178R.drawable.ic_amenity_gym));
        this.icons.put(AmenityData.AMENITY_SPA, Integer.valueOf(C1178R.drawable.ic_amenity_spa));
        this.icons.put(AmenityData.AMENITY_PETS, Integer.valueOf(C1178R.drawable.ic_amenity_pets));
        this.icons.put(AmenityData.AMENITY_SWIMMING_POOL, Integer.valueOf(C1178R.drawable.ic_amenity_pool));
        this.icons.put(AmenityData.AMENITY_LGBT_FRIENDLY, Integer.valueOf(C1178R.drawable.ic_amenity_lgbt));
        this.icons.put(AmenityData.AMENITY_BATHROOM, Integer.valueOf(C1178R.drawable.ic_amenity_bathroom));
        this.icons.put(AmenityData.AMENITY_SHOWER, Integer.valueOf(C1178R.drawable.ic_amenity_shower));
        this.icons.put(AmenityData.AMENITY_TV, Integer.valueOf(C1178R.drawable.ic_amenity_tv));
        this.icons.put(AmenityData.AMENITY_AIR_CONDITIONING, Integer.valueOf(C1178R.drawable.ic_amenity_air_conditioning));
        this.icons.put(AmenityData.AMENITY_SAFE_BOX, Integer.valueOf(C1178R.drawable.ic_amenity_safe));
        this.icons.put(AmenityData.AMENITY_MINI_BAR, Integer.valueOf(C1178R.drawable.ic_amenity_bar));
        this.icons.put(AmenityData.AMENITY_HAIRDRYER, Integer.valueOf(C1178R.drawable.ic_amenity_hairdryer));
        this.icons.put(AmenityData.AMENITY_COFFEE_TEA, Integer.valueOf(C1178R.drawable.ic_amenity_tea));
        this.icons.put(AmenityData.AMENITY_BATHROBES, Integer.valueOf(C1178R.drawable.ic_amenity_bathrobes));
        this.icons.put(AmenityData.AMENITY_DAILY_HOUSEKEEPING, Integer.valueOf(C1178R.drawable.ic_amenity_daily_housekeeping));
        this.icons.put(AmenityData.AMENITY_CONNECTING_ROOMS, Integer.valueOf(C1178R.drawable.ic_amenity_connecting_rooms));
        this.icons.put(AmenityData.AMENITY_SMOKING, Integer.valueOf(C1178R.drawable.ic_amenity_smoking));
        this.icons.put(AmenityData.AMENITY_WIFI_IN_ROOMS, Integer.valueOf(C1178R.drawable.ic_amenity_wifi));
        this.titleDelegates.put(AmenityData.AMENITY_PARKING, new FreeableAmenityTitleDelegate(C1178R.string.free_parking, C1178R.string.parking));
        this.titleDelegates.put(AmenityData.AMENITY_WIFI_IN_PUBLIC, new FreeableAmenityTitleDelegate(C1178R.string.free_public_wifi, C1178R.string.public_wifi));
        this.titleDelegates.put(AmenityData.AMENITY_WIFI_IN_ROOMS, new FreeableAmenityTitleDelegate(C1178R.string.free_room_wifi, C1178R.string.room_wifi));
    }

    private void setUpAmenityView(@NonNull AmenityData amenity, @NonNull Context context, @NonNull View amenityView) {
        String slug = amenity.getSlug();
        if (this.icons.containsKey(slug)) {
            ((ImageView) amenityView.findViewById(C1178R.id.amenity_icon)).setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), ((Integer) this.icons.get(slug)).intValue(), null));
        }
        TextView tv = (TextView) amenityView.findViewById(C1178R.id.amenity_name);
        if (this.titleDelegates.containsKey(slug)) {
            tv.setText(((AmenityTitleDelegate) this.titleDelegates.get(slug)).title(tv.getContext(), amenity));
        } else {
            tv.setText(amenity.getName());
        }
    }

    @NonNull
    public List<View> createViews(@NonNull ViewGroup parent, @NonNull List<AmenityData> hotelAmenities) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        List<View> views = new LinkedList();
        for (AmenityData amenity : hotelAmenities) {
            View view = inflater.inflate(C1178R.layout.amenity_item, parent, false);
            setUpAmenityView(amenity, parent.getContext(), view);
            views.add(view);
        }
        return views;
    }
}
