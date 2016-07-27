package com.hotellook.core.api;

import com.hotellook.statistics.ga.DestinationName;
import java.util.HashMap;
import java.util.Map;

public interface Constants {

    public interface Amenities {
        public static final String CONDITIONING = "air conditioning";
        public static final String FITNESS = "fitness";
        public static final String INTERNET = "internet";
        public static final String LAUNDARY = "laundry";
        public static final String PARKING = "parking";
        public static final String PETS = "pets";
        public static final String POOL = "pool";
        public static final String RESTAURANT = "restaurant";
        public static final String TV = "tv";
    }

    public interface HotelTypes {
        public static final int APARTMENT = 4;
        public static final int APART_HOTEL = 2;
        public static final int BED_BREAKFAST = 3;
        public static final int FARM = 9;
        public static final int GUESTHOUSE = 6;
        public static final int HOSTEL = 7;
        public static final int HOTEL = 1;
        public static final int LODGE = 11;
        public static final int MOTEL = 5;
        public static final Map<Integer, String> PROPERTY_TYPE_NAMES;
        public static final int RESORT = 8;
        public static final int UNKNOWN = 0;
        public static final int VACATION = 10;
        public static final int VILLA = 12;

        /* renamed from: com.hotellook.core.api.Constants.HotelTypes.1 */
        static class C11931 extends HashMap<Integer, String> {
            C11931() {
                put(Integer.valueOf(HotelTypes.UNKNOWN), "Unknown");
                put(Integer.valueOf(HotelTypes.HOTEL), DestinationName.HOTEL);
                put(Integer.valueOf(HotelTypes.APART_HOTEL), "Apart-hotel");
                put(Integer.valueOf(HotelTypes.BED_BREAKFAST), "Bed&Breakfast");
                put(Integer.valueOf(HotelTypes.APARTMENT), "Apartment");
                put(Integer.valueOf(HotelTypes.MOTEL), "Motel");
                put(Integer.valueOf(HotelTypes.GUESTHOUSE), "Guesthouse");
                put(Integer.valueOf(HotelTypes.HOSTEL), "Hostel");
                put(Integer.valueOf(HotelTypes.RESORT), "Resort");
                put(Integer.valueOf(HotelTypes.FARM), "Farm");
                put(Integer.valueOf(HotelTypes.VACATION), "Vacation");
                put(Integer.valueOf(HotelTypes.LODGE), "Lodge");
                put(Integer.valueOf(HotelTypes.VILLA), "Villa");
            }
        }

        static {
            PROPERTY_TYPE_NAMES = new C11931();
        }
    }

    public interface Options {
        public static final String BREKFAST = "breakfast";
        public static final String CARD_REQUIRED = "cardRequired";
        public static final String DEPOSIT = "deposit";
        public static final String FREE_WIFI = "freeWifi";
        public static final String HOTEL_WEBSITE = "hotelWebsite";
        public static final String REFUNDABLE = "refundable";
        public static final String SMOKING = "smoking";
    }

    public interface PriceHighlightType {
        public static final String DISCOUNT = "discount";
        public static final String MOBILE = "mobile";
        public static final String PRIVATE = "private";
    }

    public interface Scoring {
        public static final String BEACH = "beach";
        public static final String BUSINESS = "business";
        public static final String ROMANCE = "romance";
        public static final String WITH_CHILDREN = "with_children";
    }

    public interface TripTypeSplit {
        public static final String BUSINESS = "business";
        public static final String COUPLE = "couple";
        public static final String FAMILY = "family";
        public static final String SOLO = "solo";
    }
}
