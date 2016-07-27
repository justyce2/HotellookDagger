package com.hotellook.search;

public class LocationDumps {
    private final Districts districts;
    private final Hotels hotels;
    private final Pois pois;
    private final Seasons seasons;

    public LocationDumps(Hotels hotels, Pois pois, Districts districts, Seasons seasons) {
        this.hotels = hotels;
        this.pois = pois;
        this.districts = districts;
        this.seasons = seasons;
    }

    public Hotels hotels() {
        return this.hotels;
    }

    public Pois pois() {
        return this.pois;
    }

    public Districts districts() {
        return this.districts;
    }

    public Seasons seasons() {
        return this.seasons;
    }
}
