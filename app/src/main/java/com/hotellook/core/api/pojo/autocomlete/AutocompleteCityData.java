package com.hotellook.core.api.pojo.autocomlete;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hotellook.core.api.pojo.common.Coordinates;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteCityData {
    @SerializedName("_score")
    @Expose
    private double Score;
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
    private String latinFullName;
    @Expose
    private Coordinates location;
    @Expose
    private String state;
    @Expose
    private String stateCode;

    public AutocompleteCityData() {
        this.iata = new ArrayList();
    }

    public String getCity() {
        return this.city;
    }

    public String getLatinFullName() {
        return this.latinFullName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public List<String> getIata() {
        return this.iata;
    }

    public String getCountry() {
        return this.country;
    }

    public int getHotelsCount() {
        return this.hotelsCount;
    }

    public String getFullname() {
        return this.fullname;
    }

    public double getScore() {
        return this.Score;
    }

    public int getId() {
        return this.id;
    }

    public String getStateCode() {
        return this.stateCode;
    }

    public String getState() {
        return this.state;
    }
}
