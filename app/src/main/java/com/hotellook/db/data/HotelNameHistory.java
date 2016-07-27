package com.hotellook.db.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class HotelNameHistory {
    public static final String HOTEL_NAME = "hotel_name";
    public static final String VERSION = "version";
    @DatabaseField(columnName = "hotel_name", id = true)
    private String hotelName;
    @DatabaseField(columnName = "version")
    private long timestamp;

    public HotelNameHistory(String hotelName, long timestamp) {
        this.hotelName = hotelName;
        this.timestamp = timestamp;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
