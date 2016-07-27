package com.hotellook.api;

public class RequestFlags {
    public static final String FUNCTION_HOTEL_SEARCH_FROM_SEARCH_FORM = "searchForm";
    public static final String FUNCTION_SOURCE_FAVORITES = "favourites";
    public static final String FUNCTION_SOURCE_MAP = "map";
    public static final String FUNCTION_SOURCE_RESULTS = "searchResults";
    public static final String FUNCTION_SOURCE_SEARCH_FORM = "searchHotel";
    public static final String SECTION_TONIGHT = "tonight";
    private String badge;
    private String func;
    private String hls;
    private String marker;
    private String section;
    private String source;
    private String utmDetail;

    public RequestFlags(String marker, String source, String section, String func, String badge, String hls, String utmDetail) {
        this.marker = marker;
        this.source = source;
        this.section = section;
        this.func = func;
        this.badge = badge;
        this.hls = hls;
        this.utmDetail = utmDetail;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String mSource) {
        this.source = mSource;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String mSection) {
        this.section = mSection;
    }

    public String getFunc() {
        return this.func;
    }

    public void setFunc(String mFunc) {
        this.func = mFunc;
    }

    public String getBadge() {
        return this.badge;
    }

    public void setBadge(String mBadge) {
        this.badge = mBadge;
    }

    public String getHls() {
        return this.hls;
    }

    public String getUtmDetail() {
        return this.utmDetail;
    }

    public String getMarker() {
        return this.marker;
    }
}
