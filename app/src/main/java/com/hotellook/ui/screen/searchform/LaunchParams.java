package com.hotellook.ui.screen.searchform;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class LaunchParams {
    private static final String ADULTS = "adults";
    private static final String AUTO_DATES = "autoDates";
    private static final String CHECK_IN_DATE = "checkIn";
    private static final String CHECK_OUT_DATE = "checkOut";
    private static final String CHILDREN = "children";
    private static final String DESTINATION = "destination";
    private static final String HLS = "hls";
    private static final String HOTEL_ID = "hotelId";
    private static final String LOCATION_ID = "locationId";
    private static final String MARKER = "marker";
    private static final String START_SEARCH = "startSearch";
    private final Integer adults;
    private final Integer autoDates;
    private final String checkIn;
    private final String checkOut;
    private final String children;
    private final String destination;
    private final String hls;
    private final Long hotelId;
    private final Integer locationId;
    private final String marker;
    private final boolean startSearch;
    private final String utmDetails;

    public LaunchParams(long hotelId, String checkInDate, String checkOutDate, int adults) {
        this.hotelId = Long.valueOf(hotelId);
        this.checkIn = checkInDate;
        this.checkOut = checkOutDate;
        this.adults = Integer.valueOf(adults);
        this.destination = null;
        this.locationId = null;
        this.children = null;
        this.startSearch = false;
        this.hls = null;
        this.utmDetails = null;
        this.marker = null;
        this.autoDates = null;
    }

    public LaunchParams(int locationId, String checkInDate, String checkOutDate, int adults) {
        this.hotelId = null;
        this.checkIn = checkInDate;
        this.checkOut = checkOutDate;
        this.adults = Integer.valueOf(adults);
        this.destination = null;
        this.locationId = Integer.valueOf(locationId);
        this.children = null;
        this.startSearch = false;
        this.hls = null;
        this.utmDetails = null;
        this.marker = null;
        this.autoDates = null;
    }

    public LaunchParams(String iata, String checkInDate, String checkOutDate, int adults) {
        this.destination = iata;
        this.checkIn = checkInDate;
        this.checkOut = checkOutDate;
        this.adults = Integer.valueOf(adults);
        this.locationId = null;
        this.children = null;
        this.startSearch = false;
        this.hls = null;
        this.utmDetails = null;
        this.marker = null;
        this.autoDates = null;
        this.hotelId = null;
    }

    public LaunchParams(@NonNull Uri uri) {
        this.adults = parseInt(uri, ADULTS);
        this.locationId = parseInt(uri, LOCATION_ID);
        this.hotelId = parseLong(uri, HOTEL_ID);
        this.children = uri.getQueryParameter(CHILDREN);
        this.destination = uri.getQueryParameter(DESTINATION);
        this.checkIn = uri.getQueryParameter(CHECK_IN_DATE);
        this.checkOut = uri.getQueryParameter(CHECK_OUT_DATE);
        this.hls = uri.getQueryParameter(HLS);
        this.startSearch = uri.getBooleanQueryParameter(START_SEARCH, false);
        this.utmDetails = parseUtmDetails(uri);
        this.marker = parseMarker(uri);
        this.autoDates = parseInt(uri, AUTO_DATES);
    }

    public String parseMarker(Uri uri) {
        String marker = uri.getQueryParameter(MARKER);
        return isAffiliateMarker(marker) ? null : marker;
    }

    public boolean isAffiliateMarker(String marker) {
        if (marker != null) {
            return consistsOfNums(marker);
        }
        return false;
    }

    public boolean consistsOfNums(String str) {
        return Pattern.compile("^\\d{5}").matcher(str).matches();
    }

    public String parseUtmDetails(Uri uri) {
        Set<String> utmKeys = new TreeSet();
        for (String key : uri.getQueryParameterNames()) {
            if (key.toLowerCase().startsWith("utm_")) {
                utmKeys.add(key);
            }
        }
        if (utmKeys.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String utmKey : utmKeys) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(utmKey).append(SimpleComparison.EQUAL_TO_OPERATION).append(uri.getQueryParameter(utmKey));
        }
        return sb.toString();
    }

    private Integer parseInt(Uri uri, String paramKey) {
        String adultsString = uri.getQueryParameter(paramKey);
        if (!TextUtils.isEmpty(adultsString)) {
            try {
                return Integer.valueOf(Integer.parseInt(adultsString));
            } catch (Exception e) {
            }
        }
        return null;
    }

    private Long parseLong(Uri uri, String paramKey) {
        String adultsString = uri.getQueryParameter(paramKey);
        if (!TextUtils.isEmpty(adultsString)) {
            try {
                return Long.valueOf(Long.parseLong(adultsString));
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Nullable
    public Integer getAdults() {
        return this.adults;
    }

    @Nullable
    public String getCheckIn() {
        return this.checkIn;
    }

    @Nullable
    public String getCheckOut() {
        return this.checkOut;
    }

    @Nullable
    public String getDestination() {
        return this.destination;
    }

    @Nullable
    public Integer getLocationId() {
        return this.locationId;
    }

    @Nullable
    public Long getHotelId() {
        return this.hotelId;
    }

    @Nullable
    public String getChildren() {
        return this.children;
    }

    @Nullable
    public String getHls() {
        return this.hls;
    }

    @Nullable
    public String getUtmDetails() {
        return this.utmDetails;
    }

    @Nullable
    public String getMarker() {
        return this.marker;
    }

    @Nullable
    public Integer getAutoDates() {
        return this.autoDates;
    }

    public boolean isSearchStartRequested() {
        return this.startSearch;
    }
}
