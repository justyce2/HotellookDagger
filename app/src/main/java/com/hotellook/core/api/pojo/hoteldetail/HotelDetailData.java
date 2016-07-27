package com.hotellook.core.api.pojo.hoteldetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.common.Poi;
import java.util.List;
import java.util.Map;

public class HotelDetailData {
    private static final int AMENITY_PARKING = 1;
    private static final String HOTEL_AMENITIES = "hotel";
    private static final String LANGUAGE_AMENITIES = "language";
    private static final String ROOM_AMENITIES = "room";
    @Expose
    private String address;
    @Expose
    private Map<Integer, String> amenitiesShort;
    @Expose
    private Map<String, List<AmenityData>> amenitiesV2;
    @Expose
    private String checkIn;
    @Expose
    private String checkOut;
    @Expose
    private int cntRooms;
    @Expose
    private String countryName;
    @Expose
    private String description;
    @Expose
    private Double distance;
    private List<District> districts;
    @Expose
    private FoursquareData foursquare;
    @SerializedName("4sqUsersCnt")
    @Expose
    private int foursquareCheckInCount;
    @Expose
    private boolean hasRentals;
    @Expose
    private long id;
    @Expose
    private boolean isRentals;
    @Expose
    private String latinName;
    @Expose
    private Coordinates location;
    @Expose
    private String locationFullName;
    @Expose
    private int locationId;
    @Expose
    private String locationName;
    @Expose
    private String name;
    @Expose
    private int photoCount;
    @SerializedName("poi_distance")
    private Map<Integer, Integer> poiDistance;
    @SerializedName("poi")
    private List<Poi> pois;
    @Expose
    private int propertyType;
    private String propertyTypeName;
    @Expose
    private int rating;
    @Expose
    private int stars;
    @Expose
    private TrustYouData trustyou;

    public List<AmenityData> getHotelAmenities() {
        return (List) this.amenitiesV2.get(HOTEL_AMENITIES);
    }

    public List<AmenityData> getRoomAmenities() {
        return (List) this.amenitiesV2.get(ROOM_AMENITIES);
    }

    public List<AmenityData> getLanguageAmenities() {
        return (List) this.amenitiesV2.get(LANGUAGE_AMENITIES);
    }

    public long getId() {
        return this.id;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public int getCntRooms() {
        return this.cntRooms;
    }

    public Map<Integer, String> getAmenitiesShort() {
        return this.amenitiesShort;
    }

    public String getLatinName() {
        return this.latinName;
    }

    public int getStars() {
        return this.stars;
    }

    public String getName() {
        return this.name;
    }

    public int getPropertyType() {
        return this.propertyType;
    }

    public String getPropertyTypeName() {
        return this.propertyTypeName;
    }

    public int getPhotoCount() {
        return this.photoCount;
    }

    public String getAddress() {
        return this.address;
    }

    public int getFoursquareCheckInCount() {
        return this.foursquareCheckInCount;
    }

    public Double getDistance() {
        return this.distance;
    }

    public int getRating() {
        return this.rating;
    }

    public String getLocationFullName() {
        return this.locationFullName;
    }

    public String getCheckIn() {
        return this.checkIn;
    }

    public String getCheckOut() {
        return this.checkOut;
    }

    public TrustYouData getTrustyou() {
        return this.trustyou;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public FoursquareData getFoursquare() {
        return this.foursquare;
    }

    public int getLocationId() {
        return this.locationId;
    }

    public boolean hasParking() {
        return this.amenitiesShort.containsKey(Integer.valueOf(AMENITY_PARKING));
    }

    public boolean isRentals() {
        return this.isRentals;
    }

    public boolean hasRentals() {
        return this.hasRentals;
    }

    public List<Poi> getPois() {
        return this.pois;
    }

    public Map<Integer, Integer> getPoiDistance() {
        return this.poiDistance;
    }

    public List<District> getDistricts() {
        return this.districts;
    }
}
