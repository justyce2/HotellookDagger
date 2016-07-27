package com.hotellook.db.data;

import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.utils.LocationUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class PoiHistoryItem {
    public static final String CITY_ID = "cityId";
    public static final String VERSION = "version";
    @DatabaseField
    private String category;
    @DatabaseField(columnName = "cityId")
    private int cityId;
    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private double lat;
    @DatabaseField
    private double lon;
    @DatabaseField
    private String name;
    @DatabaseField
    private float rating;
    @DatabaseField(columnName = "version")
    private long version;

    public PoiHistoryItem(int cityId, Poi poi, long version) {
        this.cityId = cityId;
        this.id = poi.getId();
        this.category = poi.getCategory();
        this.name = poi.getName();
        this.rating = poi.getRating();
        this.lat = poi.getLocation().getLat();
        this.lon = poi.getLocation().getLon();
        this.version = version;
    }

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
        return LocationUtils.toCoordinates(this.lat, this.lon);
    }

    public Poi toPoi() {
        Poi poi = new Poi();
        poi.setId(this.id);
        poi.setCategory(this.category);
        poi.setName(this.name);
        poi.setLocation(getLocation());
        poi.setRating(this.rating);
        return poi;
    }

    public int getCityId() {
        return this.cityId;
    }

    public long getVersion() {
        return this.version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String toString() {
        return "PoiHistoryItem{id=" + this.id + ", cityId=" + this.cityId + ", category='" + this.category + '\'' + ", name='" + this.name + '\'' + ", rating=" + this.rating + ", lat=" + this.lat + ", lon=" + this.lon + ", version=" + this.version + '}';
    }
}
