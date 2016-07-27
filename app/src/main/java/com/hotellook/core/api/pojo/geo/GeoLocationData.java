package com.hotellook.core.api.pojo.geo;

import com.google.gson.annotations.Expose;
import com.hotellook.core.api.pojo.common.Coordinates;
import java.util.ArrayList;
import java.util.List;

public class GeoLocationData {
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private String countryCode;
    @Expose
    private String fullname;
    @Expose
    private int hotelsCount;
    @Expose
    private List<String> iata;
    @Expose
    private int id;
    @Expose
    private Coordinates location;
    @Expose
    private List<String> locationAltNames;
    @Expose
    private String state;
    @Expose
    private String type;

    public GeoLocationData() {
        this.locationAltNames = new ArrayList();
        this.iata = new ArrayList();
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public List<String> getLocationAltNames() {
        return this.locationAltNames;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public String getFullname() {
        return this.fullname;
    }

    public int getId() {
        return this.id;
    }

    public List<String> getIata() {
        return this.iata;
    }

    public int getHotelsCount() {
        return this.hotelsCount;
    }

    public String getType() {
        return this.type;
    }

    public String getState() {
        return this.state;
    }
}
