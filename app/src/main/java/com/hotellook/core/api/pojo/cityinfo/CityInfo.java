package com.hotellook.core.api.pojo.cityinfo;

import com.hotellook.core.api.pojo.common.Coordinates;
import java.util.List;

public class CityInfo {
    private Boundary boundary;
    private String city;
    private String country;
    private String countryCode;
    private int countryId;
    private String fullname;
    private List<Coordinates> hotelPins;
    private int hotelsCount;
    private List<String> iata;
    private int id;
    private String latinCity;
    private String latinCountry;
    private String latinFullname;
    private Coordinates location;
    private int parentId;
    private String state;
    private String type;

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public int getCountryId() {
        return this.countryId;
    }

    public String getFullname() {
        return this.fullname;
    }

    public int getHotelsCount() {
        return this.hotelsCount;
    }

    public List<String> getIata() {
        return this.iata;
    }

    public int getId() {
        return this.id;
    }

    public String getLatinCity() {
        return this.latinCity;
    }

    public String getLatinCountry() {
        return this.latinCountry;
    }

    public String getLatinFullname() {
        return this.latinFullname;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public int getParentId() {
        return this.parentId;
    }

    public String getType() {
        return this.type;
    }

    public Boundary getBoundary() {
        return this.boundary;
    }

    public List<Coordinates> getHotelPins() {
        return this.hotelPins;
    }

    public String getState() {
        return this.state;
    }
}
