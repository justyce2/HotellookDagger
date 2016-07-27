package com.hotellook.core.api.pojo.autocomlete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hotellook.core.api.pojo.common.Coordinates;

public class AutocompleteHotelData {
    @SerializedName("_score")
    @Expose
    private double Score;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String hotelFullName;
    @Expose
    private long id;
    @Expose
    private String latinLocationFullName;
    @Expose
    private Coordinates location;
    @Expose
    private String locationFullName;
    @Expose
    private int locationHotelsCount;
    @Expose
    private int locationId;
    @Expose
    private String name;
    @Expose
    private String state;

    public String getCity() {
        return this.city;
    }

    public String getLocationFullName() {
        return this.locationFullName;
    }

    public String getLatinLocationFullName() {
        return this.latinLocationFullName;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public String getAddress() {
        return this.address;
    }

    public String getHotelFullName() {
        return this.hotelFullName;
    }

    public int getLocationId() {
        return this.locationId;
    }

    public String getCountry() {
        return this.country;
    }

    public long getId() {
        return this.id;
    }

    public double getScore() {
        return this.Score;
    }

    public String getState() {
        return this.state;
    }

    public int getLocationHotelsCount() {
        return this.locationHotelsCount;
    }

    public String getName() {
        return this.name;
    }
}
