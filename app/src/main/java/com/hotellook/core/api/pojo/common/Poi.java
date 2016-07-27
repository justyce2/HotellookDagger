package com.hotellook.core.api.pojo.common;

public class Poi {
    public static final String CATEGORY_AIRPORT = "airport";
    public static final String CATEGORY_BEACH = "beach";
    public static final String CATEGORY_BUSINESS = "business";
    public static final String CATEGORY_METRO_STATION = "metro_station";
    public static final String CATEGORY_SIGHT = "sight";
    public static final String CATEGORY_SKI = "ski_lift";
    public static final String CATEGORY_STADIUM = "stadium";
    public static final String CATEGORY_TRAIN_STATION = "train_station";
    private String category;
    private int id;
    private Coordinates location;
    private String name;
    private float rating;

    public int getId() {
        return this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public String getName() {
        return this.name;
    }

    public float getRating() {
        return this.rating;
    }

    public Coordinates getLocation() {
        return this.location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setLocation(Coordinates location) {
        this.location = location;
    }
}
