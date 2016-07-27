package com.hotellook.api.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import com.hotellook.HotellookApplication;
import com.hotellook.events.SearchParamsUpdatedEvent;
import com.hotellook.search.ServerDateFormatter;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class SearchFormPreferences {
    public static final int DEFAULT_ADULTS = 2;
    public static final int NOT_DEFINED = -1;
    protected static final String PARAM_ADULTS = "ADULTS";
    protected static final String PARAM_CHECK_IN = "CHECK_IN";
    protected static final String PARAM_CHECK_OUT = "CHECK_OUT";
    protected static final String PARAM_CITY = "CITY";
    protected static final String PARAM_CITY_ID = "CITY_ID";
    protected static final String PARAM_COUNTRY = "COUNTRY";
    protected static final String PARAM_DESTINATION_TYPE = "DESTINATION_TYPE";
    protected static final String PARAM_HOTEL = "HOTEL";
    protected static final String PARAM_HOTELS_NUM = "HOTELS_NUM";
    protected static final String PARAM_HOTEL_ID = "HOTEL_ID";
    protected static final String PARAM_KIDS = "KIDS";
    protected static final String PARAM_LAST_UPDATE_TIMESTAMP = "PARAM_LAST_UPDATE_TIMESTAMP";
    protected static final String PARAM_LAT = "LAT";
    protected static final String PARAM_LATIN_CITY_NAME = "LATIN_CITY_NAME";
    protected static final String PARAM_LON = "LON";
    protected static final String PARAM_STATE = "STATE";
    public static final String SEARCH_FORM_DATA = "SEARCH_FORM_DATA";
    private final SharedPreferences preferences;

    public SearchFormPreferences(Context context) {
        this.preferences = context.getSharedPreferences(SEARCH_FORM_DATA, 0);
    }

    public String getCity(String defaultCity) {
        return this.preferences.getString(PARAM_CITY, defaultCity);
    }

    public String getCountry(String defaultCountry) {
        return this.preferences.getString(PARAM_COUNTRY, defaultCountry);
    }

    public String getLatinCityName() {
        return this.preferences.getString(PARAM_LATIN_CITY_NAME, BuildConfig.FLAVOR);
    }

    public int getAdults() {
        return this.preferences.getInt(PARAM_ADULTS, DEFAULT_ADULTS);
    }

    public int getHotelsNum() {
        return this.preferences.getInt(PARAM_HOTELS_NUM, NOT_DEFINED);
    }

    public int getCityId() {
        return this.preferences.getInt(PARAM_CITY_ID, NOT_DEFINED);
    }

    public long getHotelId() {
        return this.preferences.getLong(PARAM_HOTEL_ID, -1);
    }

    public String getState() {
        return this.preferences.getString(PARAM_STATE, null);
    }

    public int getDestinationType() {
        return this.preferences.getInt(PARAM_DESTINATION_TYPE, DestinationType.CITY);
    }

    public String getHotel() {
        return this.preferences.getString(PARAM_HOTEL, null);
    }

    public long getLastUpdateTimestamp() {
        return this.preferences.getLong(PARAM_LAST_UPDATE_TIMESTAMP, 0);
    }

    public String getKids() {
        return this.preferences.getString(PARAM_KIDS, null);
    }

    public float getLat() {
        return this.preferences.getFloat(PARAM_LAT, 0.0f);
    }

    public float getLon() {
        return this.preferences.getFloat(PARAM_LON, 0.0f);
    }

    public String getCheckInDate() {
        return this.preferences.getString(PARAM_CHECK_IN, null);
    }

    public String getCheckOutDate() {
        return this.preferences.getString(PARAM_CHECK_OUT, null);
    }

    public void save(SearchFormData searchFormData) {
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        Location location = searchFormData.getLocation();
        this.preferences.edit().putString(PARAM_CITY, searchFormData.getCity()).putString(PARAM_STATE, searchFormData.getState()).putString(PARAM_COUNTRY, searchFormData.getCountry()).putInt(PARAM_ADULTS, searchFormData.getAdults()).putInt(PARAM_HOTELS_NUM, searchFormData.getHotelsNum()).putString(PARAM_CHECK_IN, dateFormatter.format(searchFormData.getCheckInCal())).putString(PARAM_CHECK_OUT, dateFormatter.format(searchFormData.getCheckOutCal())).putInt(PARAM_CITY_ID, searchFormData.getCityId()).putInt(PARAM_DESTINATION_TYPE, searchFormData.getDestinationType()).putString(PARAM_HOTEL, searchFormData.getHotel()).putString(PARAM_KIDS, searchFormData.getKidsString()).putFloat(PARAM_LAT, (float) location.getLatitude()).putFloat(PARAM_LON, (float) location.getLongitude()).putLong(PARAM_HOTEL_ID, searchFormData.getHotelId()).putLong(PARAM_LAST_UPDATE_TIMESTAMP, System.currentTimeMillis()).putString(PARAM_LATIN_CITY_NAME, searchFormData.latinCityName()).apply();
        HotellookApplication.eventBus().post(new SearchParamsUpdatedEvent());
    }
}
