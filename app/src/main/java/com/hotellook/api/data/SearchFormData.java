package com.hotellook.api.data;

import android.content.Context;
import android.location.Location;
import android.support.annotation.Nullable;
import com.hotellook.C1178R;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.SearchParams;
import com.hotellook.search.SearchParams.Builder;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.hotellook.ui.screen.searchform.LaunchParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import com.hotellook.utils.LocationUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class SearchFormData {
    private static final long TIMEOUT_FOR_SHOWING_PREVIOUS_DATE_IN_MLS;
    private int adults;
    private Calendar checkInCal;
    private Calendar checkOutCal;
    private String city;
    private int cityId;
    private String country;
    private int destinationType;
    private String hotel;
    private long hotelId;
    private int hotelsNum;
    private List<Integer> kids;
    @Nullable
    private String latinCityName;
    private Location location;
    private long searchFormDataLastUpdateTimestamp;
    private SearchFormPreferences sharedPreferences;
    private String state;

    static {
        TIMEOUT_FOR_SHOWING_PREVIOUS_DATE_IN_MLS = TimeUnit.HOURS.toMillis(3);
    }

    public SearchFormData(Context context, SearchFormPreferences preferences) {
        this.kids = new ArrayList();
        this.hotelId = -1;
        this.searchFormDataLastUpdateTimestamp = 0;
        this.sharedPreferences = preferences;
        this.destinationType = preferences.getDestinationType();
        this.city = preferences.getCity(getDefaultCityTxt(context));
        this.country = preferences.getCountry(getDefaultCountryTxt(context));
        this.adults = preferences.getAdults();
        this.hotelsNum = preferences.getHotelsNum();
        this.cityId = preferences.getCityId();
        this.hotelId = preferences.getHotelId();
        this.state = preferences.getState();
        this.hotel = preferences.getHotel();
        this.latinCityName = preferences.getLatinCityName();
        this.searchFormDataLastUpdateTimestamp = preferences.getLastUpdateTimestamp();
        setKidsList(preferences.getKids());
        this.location = LocationUtils.from(preferences.getLat(), preferences.getLon());
        setDates(preferences.getCheckInDate(), preferences.getCheckOutDate());
        if (isMapType()) {
            this.hotelId = -1;
            this.cityId = -1;
            this.hotel = null;
            this.city = null;
            this.state = null;
            this.country = null;
            this.hotelsNum = -1;
            this.latinCityName = null;
        } else if (noData()) {
            addDefaultCityData();
        }
    }

    public SearchFormData(SearchParams searchParams, BasicHotelData basicHotelData, CityInfo cityInfo) {
        this.kids = new ArrayList();
        this.hotelId = -1;
        this.searchFormDataLastUpdateTimestamp = 0;
        this.city = searchParams.destinationName();
        this.country = cityInfo.getCountry();
        this.checkInCal = searchParams.checkIn();
        this.checkOutCal = searchParams.checkOut();
        this.adults = searchParams.adults();
        this.cityId = searchParams.locationId();
        this.destinationType = DestinationType.HOTEL;
        this.hotel = basicHotelData.name();
        this.kids = searchParams.kids();
        this.location = searchParams.location();
        this.hotelId = basicHotelData.id();
        this.latinCityName = cityInfo.getLatinCity();
    }

    public SearchFormData(LaunchParams launchParams, BasicHotelData basicHotelData, CityInfo cityInfo) {
        this.kids = new ArrayList();
        this.hotelId = -1;
        this.searchFormDataLastUpdateTimestamp = 0;
        this.city = cityInfo.getCity();
        this.country = cityInfo.getCountry();
        setDates(launchParams.getCheckIn(), launchParams.getCheckOut());
        this.adults = launchParams.getAdults().intValue();
        this.cityId = cityInfo.getId();
        this.destinationType = DestinationType.HOTEL;
        this.hotel = basicHotelData.name();
        setKidsList(launchParams.getChildren());
        this.location = LocationUtils.from(basicHotelData.location());
        this.hotelId = basicHotelData.id();
        this.latinCityName = cityInfo.getLatinCity();
    }

    private void setDates(String checkInDateStr, String checkOutDateStr) {
        if (checkInDateStr == null || checkOutDateStr == null) {
            setDefaultDates();
        } else {
            ServerDateFormatter dateFormatter = new ServerDateFormatter();
            this.checkInCal = dateFormatter.parse(checkInDateStr);
            this.checkOutCal = dateFormatter.parse(checkOutDateStr);
        }
        Calendar today = DateUtils.getTodayCalendar();
        Calendar actualToday = (Calendar) today.clone();
        if (DateUtils.isPreviousDayActualAnywhereOnThePlanet()) {
            actualToday.add(5, -1);
        }
        int daysBetween = DateUtils.daysBetween(this.checkInCal, this.checkOutCal);
        if (this.checkInCal == null || this.checkInCal.before(actualToday) || daysBetween > 30 || isConditionToShowPreviousDayPassed(this.checkInCal, today, actualToday)) {
            setDefaultDates();
        }
    }

    private boolean isConditionToShowPreviousDayPassed(Calendar checkInCal, Calendar today, Calendar actualToday) {
        return DateUtils.areDatesEqualsByDay(checkInCal, actualToday) && actualToday.before(today) && isTimeoutToShowPreviousDatePassed();
    }

    private boolean isTimeoutToShowPreviousDatePassed() {
        return System.currentTimeMillis() - this.searchFormDataLastUpdateTimestamp > TIMEOUT_FOR_SHOWING_PREVIOUS_DATE_IN_MLS;
    }

    private void setKidsList(String kidsStr) {
        this.kids = KidsSerializer.parse(kidsStr);
        if (this.kids.size() > 4 || this.kids.size() < 0) {
            this.kids.clear();
        }
    }

    private void setDefaultDates() {
        this.checkInCal = DateUtils.getTodayCalendar();
        this.checkOutCal = DateUtils.getTodayCalendar();
        this.checkOutCal.add(5, 1);
    }

    public String getDateString(Context context) {
        if (AndroidUtils.isPhone(context) && AndroidUtils.smallDensity(context)) {
            return DateUtils.printShortDate(this.checkInCal) + " \u2014 " + DateUtils.printShortDate(this.checkOutCal);
        }
        return DateUtils.printMediumDate(this.checkInCal) + " \u2014 " + DateUtils.printMediumDate(this.checkOutCal);
    }

    public Location getLocation() {
        return this.location;
    }

    public String getHotelsNumTxt(Context context) {
        if (this.hotelsNum == -1) {
            return BuildConfig.FLAVOR;
        }
        return String.format("%,d", new Object[]{Integer.valueOf(this.hotelsNum)}) + " " + context.getResources().getQuantityString(C1178R.plurals.sf_hotel_num, this.hotelsNum);
    }

    public void saveData() {
        if (this.sharedPreferences != null) {
            this.sharedPreferences.save(this);
        }
    }

    public void addDefaultCityData() {
        DefaultSearchFormData defaultValues = DefaultSearchFormData.create(HotellookApplication.getApp().getResources());
        this.city = defaultValues.city;
        this.country = defaultValues.country;
        this.hotelsNum = defaultValues.hotelsNum;
        this.cityId = defaultValues.cityId;
        this.destinationType = defaultValues.destinationType;
        this.location = defaultValues.location;
        this.checkInCal = defaultValues.checkInCal;
        this.checkOutCal = defaultValues.checkOutCal;
        this.latinCityName = defaultValues.latinCityName;
        saveData();
    }

    public boolean noData() {
        return this.cityId == -1 || this.hotelsNum == -1;
    }

    public String getDestinationName() {
        switch (this.destinationType) {
            case DestinationType.CITY /*401*/:
                return this.city;
            case DestinationType.HOTEL /*402*/:
                return this.hotel;
            default:
                return null;
        }
    }

    private String getCityLatinName() {
        return this.latinCityName;
    }

    public String getDestinationLocation() {
        String stateStr = this.state != null ? this.state + ", " : BuildConfig.FLAVOR;
        switch (this.destinationType) {
            case DestinationType.CITY /*401*/:
                return stateStr + this.country;
            case DestinationType.HOTEL /*402*/:
                return stateStr + this.city + ", " + this.country;
            default:
                return null;
        }
    }

    public int getAdults() {
        return this.adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(DestinationData cityData) {
        this.city = cityData.getCityName();
        this.country = cityData.getCountry();
        this.cityId = cityData.getCityId();
        this.destinationType = DestinationType.CITY;
        this.hotelsNum = cityData.getHotelsNum();
        this.state = cityData.getState();
        this.location = cityData.getLocation();
        this.hotelId = -1;
    }

    public int getCityId() {
        return this.cityId;
    }

    public int getDestinationType() {
        return this.destinationType;
    }

    @Nullable
    public Date getCheckInDate() {
        return this.checkInCal == null ? null : this.checkInCal.getTime();
    }

    public void setCheckInDate(Date date) {
        this.checkInCal = Calendar.getInstance();
        this.checkInCal.setTime(date);
    }

    public Date getCheckOutDate() {
        return this.checkOutCal.getTime();
    }

    public void setCheckOutDate(Date date) {
        this.checkOutCal = Calendar.getInstance();
        this.checkOutCal.setTime(date);
    }

    public List<Integer> getKids() {
        return this.kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public String getKidsString() {
        if (this.kids == null || this.kids.size() == 0) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer kidAge : this.kids) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(kidAge);
        }
        return sb.toString();
    }

    public int getKidsCount() {
        return this.kids.size();
    }

    public long getHotelId() {
        return this.hotelId;
    }

    public boolean hasData() {
        return !noData();
    }

    public void updateWithLaunchParams(LaunchParams launchParams) {
        reset(launchParams);
        saveData();
    }

    public void reset(LaunchParams launchParams) {
        resetDestinationData();
        setDates(launchParams.getCheckIn(), launchParams.getCheckOut());
        this.adults = isAdultsValueValid(launchParams.getAdults()) ? launchParams.getAdults().intValue() : 2;
        this.cityId = launchParams.getLocationId() == null ? -1 : launchParams.getLocationId().intValue();
        this.destinationType = launchParams.getHotelId() == null ? DestinationType.CITY : DestinationType.HOTEL;
        this.hotel = null;
        setKidsList(launchParams.getChildren());
        this.location = LocationUtils.from(0.0f, 0.0f);
        this.hotelId = launchParams.getHotelId() == null ? -1 : launchParams.getHotelId().longValue();
        this.hotelsNum = -1;
    }

    public boolean isAdultsValueValid(Integer adults) {
        return adults != null && adults.intValue() > 0 && adults.intValue() <= HotellookApplication.getApp().getResources().getStringArray(C1178R.array.sf_adults_array).length;
    }

    private void resetDestinationData() {
        Context context = HotellookApplication.getApp();
        this.city = getDefaultCityTxt(context);
        this.country = getDefaultCountryTxt(context);
        this.state = null;
        this.hotelsNum = -1;
    }

    private String getDefaultCountryTxt(Context context) {
        return context.getString(C1178R.string.sf_default_country);
    }

    private String getDefaultCityTxt(Context context) {
        return context.getString(C1178R.string.sf_default_city);
    }

    public String getState() {
        return this.state;
    }

    public String getCountry() {
        return this.country;
    }

    public int getHotelsNum() {
        return this.hotelsNum;
    }

    public Calendar getCheckInCal() {
        return this.checkInCal;
    }

    public Calendar getCheckOutCal() {
        return this.checkOutCal;
    }

    public String getHotel() {
        return this.hotel;
    }

    public void setHotel(DestinationData hotelData) {
        this.hotel = hotelData.getHotelName();
        this.country = hotelData.getCountry();
        this.city = hotelData.getCityName();
        this.cityId = hotelData.getCityId();
        this.destinationType = DestinationType.HOTEL;
        this.hotelsNum = hotelData.getHotelsNum();
        this.state = hotelData.getState();
        this.location = hotelData.getLocation();
        this.hotelId = hotelData.getHotelId();
    }

    public void updateWithUserLocationDestination(Location location) {
        updateWithLocationDestination(DestinationType.USER_LOCATION, location);
    }

    public void updateWithLocationDestination(Location location) {
        updateWithLocationDestination(DestinationType.MAP_POINT, location);
    }

    private void updateWithLocationDestination(int destinationType, Location location) {
        this.destinationType = destinationType;
        this.hotelId = -1;
        this.cityId = -1;
        this.hotel = null;
        this.city = null;
        this.state = null;
        this.country = null;
        this.hotelsNum = -1;
        this.location = location;
        saveData();
    }

    public boolean isMapType() {
        return this.destinationType == DestinationType.USER_LOCATION || this.destinationType == DestinationType.MAP_POINT;
    }

    public boolean isCityType() {
        return this.destinationType == DestinationType.CITY;
    }

    public int getNightsCount() {
        return DateUtils.daysBetween(this.checkInCal.getTime(), this.checkOutCal.getTime());
    }

    public SearchParams toSearchParams(String currency, boolean enGatesAllowed) {
        return new Builder().locationId(getCityId()).checkIn(getCheckInCal()).checkOut(getCheckOutCal()).adults(getAdults()).kids(getKids()).location(getLocation()).hotelId(getHotelId()).destinationName(getDestinationName()).latinCityName(getCityLatinName()).currency(currency).destinationType(getDestinationType()).language(AndroidUtils.getLanguage()).allowEnGates(enGatesAllowed).build();
    }

    @Nullable
    public String latinCityName() {
        return this.latinCityName;
    }

    public void setLatinCityName(@Nullable String latinCityName) {
        this.latinCityName = latinCityName;
    }

    public boolean isUserCoordinatesType() {
        return this.destinationType == DestinationType.USER_LOCATION;
    }

    public boolean isLocationType() {
        return this.destinationType == DestinationType.MAP_POINT;
    }
}
