package com.hotellook.db.data;

import android.location.Location;
import com.hotellook.api.data.DestinationData;
import com.hotellook.utils.LocationUtils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DestinationPickerHistoryItem {
    @DatabaseField
    private int cityId;
    @DatabaseField
    private String cityName;
    @DatabaseField
    private String country;
    @DatabaseField(unique = true)
    private String destinationId;
    @DatabaseField
    private long hotelId;
    @DatabaseField
    private String hotelName;
    @DatabaseField
    private int hotelsNum;
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private double lat;
    @DatabaseField
    private double lon;
    @DatabaseField
    private String state;
    @DatabaseField
    private long timestamp;
    @DatabaseField
    private int type;

    public DestinationPickerHistoryItem(DestinationData data) {
        this.cityName = data.getCityName();
        this.cityId = data.getCityId();
        this.state = data.getState();
        this.country = data.getCountry();
        this.hotelsNum = data.getHotelsNum();
        this.type = data.getType();
        this.state = data.getState();
        this.hotelName = data.getHotelName();
        this.timestamp = System.currentTimeMillis();
        this.destinationId = this.cityId + this.hotelName + this.type;
        this.lat = data.getLocation().getLatitude();
        this.lon = data.getLocation().getLongitude();
        this.hotelId = data.getHotelId();
    }

    public String getCityName() {
        return this.cityName;
    }

    public int getCityId() {
        return this.cityId;
    }

    public int getHotelsNum() {
        return this.hotelsNum;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public String getCountry() {
        return this.country;
    }

    public int getType() {
        return this.type;
    }

    public String getState() {
        return this.state;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getDestinationId() {
        return this.destinationId;
    }

    public void update(DestinationPickerHistoryItem item) {
        this.timestamp = item.getTimestamp();
    }

    public Location getLocation() {
        return LocationUtils.from(this.lat, this.lon);
    }

    public long getHotelId() {
        return this.hotelId;
    }
}
