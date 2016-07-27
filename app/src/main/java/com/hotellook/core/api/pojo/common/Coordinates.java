package com.hotellook.core.api.pojo.common;

import com.google.gson.annotations.Expose;

public class Coordinates {
    @Expose
    private double lat;
    @Expose
    private double lon;

    public double getLat() {
        return this.lat;
    }

    public double getLon() {
        return this.lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
