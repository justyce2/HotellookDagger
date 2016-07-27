package com.hotellook.core.api.pojo.hoteldetail;

import android.text.TextUtils;
import java.util.HashMap;

public class AmenityData extends HashMap<String, String> {
    public static final String AMENITY_24_DESK = "24_hours_front_desk_service";
    public static final String AMENITY_AIR_CONDITIONING = "air_conditioning";
    public static final String AMENITY_BAR = "bar";
    public static final String AMENITY_BATHROBES = "bathrobes";
    public static final String AMENITY_BATHROOM = "bathtub";
    public static final String AMENITY_BUSINESS_CENTRE = "business_centre";
    public static final String AMENITY_COFFEE_TEA = "coffee_tea";
    public static final String AMENITY_CONCIERGE = "concierge_service";
    public static final String AMENITY_CONNECTING_ROOMS = "connecting_rooms";
    public static final String AMENITY_DAILY_HOUSEKEEPING = "daily_housekeeping";
    public static final String AMENITY_GYM = "gym";
    public static final String AMENITY_HAIRDRYER = "hairdryer";
    public static final String AMENITY_LAUNDRY = "laundry_service";
    public static final String AMENITY_LGBT_FRIENDLY = "lgbt_friendly";
    public static final String AMENITY_LOW_MOBILITY = "low_mobility_guests_welcome";
    public static final String AMENITY_MINI_BAR = "mini_bar";
    public static final String AMENITY_PARKING = "parking";
    public static final String AMENITY_PETS = "pets_allowed";
    public static final String AMENITY_RESTAURANT = "restaurant_cafe";
    public static final String AMENITY_SAFE_BOX = "safe_box";
    public static final String AMENITY_SHOWER = "shower";
    public static final String AMENITY_SMOKING = "smoking_room";
    public static final String AMENITY_SPA = "spa";
    public static final String AMENITY_SWIMMING_POOL = "swimming_pool";
    public static final String AMENITY_TV = "tv";
    public static final String AMENITY_WIFI_IN_PUBLIC = "wi-fi_in_public_areas";
    public static final String AMENITY_WIFI_IN_ROOMS = "wi-fi_in_rooms";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_SLUG = "slug";

    public String getName() {
        return (String) get(FIELD_NAME);
    }

    public String getSlug() {
        return (String) get(FIELD_SLUG);
    }

    public boolean isPriceUnknown() {
        return getPrice() == null;
    }

    public boolean isFree() {
        return !isPriceUnknown() && TextUtils.isEmpty(getPrice());
    }

    public String getPrice() {
        return (String) get(FIELD_PRICE);
    }
}
