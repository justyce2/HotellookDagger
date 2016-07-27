package com.hotellook.api.data;

import android.location.Location;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteCityData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteHotelData;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.db.data.DestinationPickerHistoryItem;
import com.hotellook.utils.LocationUtils;

public class DestinationData {
    private final int cityId;
    private final String cityName;
    private final String country;
    private final long hotelId;
    private final String hotelName;
    private final int hotelsNum;
    private final Location location;
    private final String state;
    private final int type;

    public DestinationData(DestinationPickerHistoryItem historyItem) {
        this.cityName = historyItem.getCityName();
        this.cityId = historyItem.getCityId();
        this.hotelsNum = historyItem.getHotelsNum();
        this.hotelName = historyItem.getHotelName();
        this.country = historyItem.getCountry();
        this.state = historyItem.getState();
        this.type = historyItem.getType();
        this.location = historyItem.getLocation();
        this.hotelId = historyItem.getHotelId();
    }

    public DestinationData(AutocompleteCityData cityData) {
        this.cityName = cityData.getCity();
        this.cityId = cityData.getId();
        this.hotelsNum = cityData.getHotelsCount();
        this.country = cityData.getCountry();
        this.state = cityData.getState();
        this.type = DestinationType.CITY;
        this.hotelName = null;
        this.location = convertToLocation(cityData.getLocation());
        this.hotelId = -1;
    }

    public DestinationData(AutocompleteHotelData hotelData) {
        this.cityName = hotelData.getCity();
        this.cityId = hotelData.getLocationId();
        this.hotelsNum = hotelData.getLocationHotelsCount();
        this.hotelName = hotelData.getName();
        this.country = hotelData.getCountry();
        this.state = hotelData.getState();
        this.type = DestinationType.HOTEL;
        this.location = convertToLocation(hotelData.getLocation());
        this.hotelId = hotelData.getId();
    }

    public DestinationData(GeoLocationData geoLocationData) {
        this.cityName = geoLocationData.getCity();
        this.cityId = geoLocationData.getId();
        this.hotelsNum = geoLocationData.getHotelsCount();
        this.country = geoLocationData.getCountry();
        this.type = DestinationType.CITY;
        this.state = geoLocationData.getState();
        this.hotelName = null;
        this.location = convertToLocation(geoLocationData.getLocation());
        this.hotelId = -1;
    }

    public DestinationData(CityInfo cityInfo) {
        this.cityName = cityInfo.getCity();
        this.cityId = cityInfo.getId();
        this.hotelsNum = cityInfo.getHotelsCount();
        this.country = cityInfo.getCountry();
        this.state = cityInfo.getState();
        this.type = DestinationType.CITY;
        this.hotelName = null;
        this.location = convertToLocation(cityInfo.getLocation());
        this.hotelId = -1;
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

    public String getState() {
        return this.state;
    }

    public int getType() {
        return this.type;
    }

    public Location getLocation() {
        return this.location;
    }

    private Location convertToLocation(Coordinates coordinates) {
        return LocationUtils.from(coordinates);
    }

    public long getHotelId() {
        return this.hotelId;
    }
}
