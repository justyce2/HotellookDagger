package com.hotellook.core.api.pojo.hotelsdump;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hotellook.core.api.Constants.HotelTypes;
import com.hotellook.core.api.pojo.common.Coordinates;
import java.util.List;
import java.util.Map;

public class HotelData {
    @Expose
    private String address;
    @Expose
    private Map<Integer, String> amenitiesShort;
    @Expose
    private String chain;
    @Expose
    private int cntRooms;
    @Expose
    private double distance;
    @SerializedName("districtId")
    private List<Integer> districtIds;
    @SerializedName("4sqUsersCnt")
    @Expose
    private int foursquareCheckInCount;
    @Expose
    private boolean hasFoursquareTips;
    @Expose
    private boolean hasTrustYouData;
    @Expose
    private long id;
    @Expose
    private String latinAddress;
    @Expose
    private String latinName;
    @Expose
    private Coordinates location;
    @Expose
    private String name;
    @Expose
    private int ordersCount;
    @Expose
    private int photoCount;
    @SerializedName("poi_distance")
    @Expose
    private Map<Integer, Integer> poiDistance;
    @Expose
    private int popularity;
    @Expose
    private int propertyType;
    @Expose
    private int rating;
    @Expose
    private Scoring scoring;
    @Expose
    private int stars;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }

    public int getCntRooms() {
        return this.cntRooms;
    }

    public void setCntRooms(int cntRooms) {
        this.cntRooms = cntRooms;
    }

    public Map<Integer, String> getAmenitiesShort() {
        return this.amenitiesShort;
    }

    public void setAmenitiesShort(Map<Integer, String> amenitiesShort) {
        this.amenitiesShort = amenitiesShort;
    }

    public String getLatinName() {
        return this.latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public int getStars() {
        return this.stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean getHasFoursquareTips() {
        return this.hasFoursquareTips;
    }

    public void setHasFoursquareTips(boolean hasFoursquareTips) {
        this.hasFoursquareTips = hasFoursquareTips;
    }

    public boolean getHasTrustYouData() {
        return this.hasTrustYouData;
    }

    public void setHasTrustYouData(boolean hasTrustYouData) {
        this.hasTrustYouData = hasTrustYouData;
    }

    public String getChain() {
        return this.chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public String getLatinAddress() {
        return this.latinAddress;
    }

    public void setLatinAddress(String latinAddress) {
        this.latinAddress = latinAddress;
    }

    public int getPhotoCount() {
        return this.photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFoursquareCheckInCount() {
        return this.foursquareCheckInCount;
    }

    public void setFoursquareCheckInCount(int foursquareCheckInCount) {
        this.foursquareCheckInCount = foursquareCheckInCount;
    }

    public int getOrdersCount() {
        return this.ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return this.popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Scoring getScoring() {
        return this.scoring;
    }

    public void setScoring(Scoring scoring) {
        this.scoring = scoring;
    }

    public Map<Integer, Integer> getPoiDistance() {
        return this.poiDistance;
    }

    public void setPoiDistance(Map<Integer, Integer> poiDistance) {
        this.poiDistance = poiDistance;
    }

    public String getPropertyTypeName() {
        return (String) HotelTypes.PROPERTY_TYPE_NAMES.get(Integer.valueOf(this.propertyType));
    }

    public List<Integer> getDistrictIds() {
        return this.districtIds;
    }
}
