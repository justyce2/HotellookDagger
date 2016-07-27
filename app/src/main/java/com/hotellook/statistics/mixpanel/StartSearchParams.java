package com.hotellook.statistics.mixpanel;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hotellook.HotellookApplication;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.Constants.Source;
import com.hotellook.statistics.StatisticPrefs;
import com.hotellook.utils.DateUtils;
import java.util.HashMap;
import java.util.Map;

public class StartSearchParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final String imageUrl;
    private final StatisticPrefs prefs;
    private final SearchParams prevSearchParams;
    private final SearchParams searchParams;
    private final Source source;

    public StartSearchParams(SearchParams searchParams, Source source, String imageUrl) {
        this.searchParams = searchParams;
        this.source = source;
        this.imageUrl = imageUrl;
        this.prefs = new StatisticPrefs(HotellookApplication.getApp());
        this.prevSearchParams = this.prefs.putSearchParamsAndGetPrevious(searchParams);
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> map = new HashMap();
        map.put(MixPanelParams.SEARCH_REFERRER, this.source.mixpanelLiteral);
        map.put(MixPanelParams.SEARCH_TYPE, getMixPanelSearchType());
        if (!(this.searchParams.isDestinationTypeUserLocation() || this.searchParams.isDestinationTypeMapPoint())) {
            map.put(MixPanelParams.SEARCH_CITY, Integer.valueOf(this.searchParams.locationId()));
        }
        if (this.searchParams.isHotelSearch()) {
            map.put(MixPanelParams.SEARCH_HOTEL, Long.valueOf(this.searchParams.hotelId()));
        }
        map.put(MixPanelParams.SEARCH_DEPTH, Integer.valueOf(DateUtils.calculateDepth(this.searchParams.checkIn())));
        map.put(MixPanelParams.SEARCH_LENGTH, Integer.valueOf(this.searchParams.nightsCount()));
        map.put(MixPanelParams.SEARCH_ADULTS, Integer.valueOf(this.searchParams.adults()));
        map.put(MixPanelParams.SEARCH_KIDS, Integer.valueOf(this.searchParams.kidsCount()));
        map.put(MixPanelParams.CURRENCY, this.searchParams.currency());
        map.put(MixPanelParams.SEARCH_PHOTO, hasPhoto());
        map.put(MixPanelParams.SEARCH_REPEAT, Boolean.valueOf(this.searchParams.equals(this.prevSearchParams)));
        map.put(MixPanelParams.SEARCH_COUNT, Integer.valueOf(this.prefs.getSearchCount()));
        map.put(MixPanelParams.SEARCH_CITY_NAME, this.searchParams.latinCityName());
        return map;
    }

    @NonNull
    private String getMixPanelSearchType() {
        if (this.searchParams.isDestinationTypeCity()) {
            return MixPanelParams.CITY;
        }
        if (this.searchParams.isDestinationTypeUserLocation()) {
            return MixPanelParams.SEARCH_TYPE_LOCATION;
        }
        if (this.searchParams.isDestinationTypeNearbyCities()) {
            return MixPanelParams.SEARCH_TYPE_NEARBY_CITIES;
        }
        if (this.searchParams.isDestinationTypeMapPoint()) {
            return MixPanelParams.SEARCH_TYPE_LOCATION;
        }
        return MixPanelParams.HOTEL;
    }

    private Boolean hasPhoto() {
        boolean z = false;
        if (TextUtils.isEmpty(this.imageUrl)) {
            return Boolean.valueOf(false);
        }
        Uri uri = Uri.parse(this.imageUrl);
        if (Fresco.getImagePipeline().isInBitmapMemoryCache(uri) || Fresco.getImagePipeline().isInDiskCache(uri).hasResult()) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> map = new HashMap();
        map.put(MixPanelParams.SEARCH_COUNT, Integer.valueOf(this.prefs.getSearchCount()));
        return map;
    }
}
