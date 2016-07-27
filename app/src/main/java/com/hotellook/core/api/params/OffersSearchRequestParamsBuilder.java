package com.hotellook.core.api.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OffersSearchRequestParamsBuilder {
    private static final String ADULTS = "adults";
    private static final String ALLOW_EN = "allowEn";
    private static final String CHECK_IN = "checkIn";
    private static final String CHECK_OUT = "checkOut";
    private static final String CHILDREN = "children";
    private static final String CURRENCY = "currency";
    private static final String DISCOUNTS = "discounts";
    private static final String FLAGS_MOBILE_BADGE = "flags[mobile_badge]";
    private static final String FLAGS_MOBILE_FUNC = "flags[mobile_func]";
    private static final String FLAGS_MOBILE_SECTION = "flags[mobile_section]";
    private static final String FLAGS_MOBILE_SOURCE = "flags[mobile_source]";
    private static final String FLAGS_UTM = "flags[utm]";
    private static final String FLAGS_UTM_DETAIL = "flags[utmDetail]";
    private static final String HOST = "host";
    private static final String HOTEL_ID = "hotelId";
    private static final String LANGUAGE = "language";
    private static final String LOCATION_ID = "locationId";
    public static final String LOCATION_IDS_FORMAT = "locationIds[%d]";
    private static final String MARKER = "marker";
    private static final String MOBILE_TOKEN = "mobileToken";
    private static final String RECEIVED_GATES_FORMAT = "receivedGates[%d]";
    private static final String UUID = "uuid";
    private Map<String, Object> params;

    public OffersSearchRequestParamsBuilder() {
        this.params = new HashMap();
    }

    public OffersSearchRequestParamsBuilder locationId(int locationId) {
        this.params.put(LOCATION_ID, Integer.valueOf(locationId));
        return this;
    }

    public OffersSearchRequestParamsBuilder locationIds(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            this.params.put(String.format(LOCATION_IDS_FORMAT, new Object[]{Integer.valueOf(i)}), ids.get(i));
        }
        return this;
    }

    public OffersSearchRequestParamsBuilder receivedGates(List<Integer> receivedGates) {
        for (int i = 0; i < receivedGates.size(); i++) {
            this.params.put(String.format(RECEIVED_GATES_FORMAT, new Object[]{Integer.valueOf(i)}), receivedGates.get(i));
        }
        return this;
    }

    public OffersSearchRequestParamsBuilder hotelId(long hotelId) {
        this.params.put(HOTEL_ID, Long.valueOf(hotelId));
        return this;
    }

    public OffersSearchRequestParamsBuilder checkIn(String checkIn) {
        this.params.put(CHECK_IN, checkIn);
        return this;
    }

    public OffersSearchRequestParamsBuilder checkOut(String checkOut) {
        this.params.put(CHECK_OUT, checkOut);
        return this;
    }

    public OffersSearchRequestParamsBuilder adults(int adults) {
        this.params.put(ADULTS, Integer.valueOf(adults));
        return this;
    }

    public OffersSearchRequestParamsBuilder children(String children) {
        this.params.put(CHILDREN, children);
        return this;
    }

    public OffersSearchRequestParamsBuilder mobileToken(String mobileToken) {
        this.params.put(MOBILE_TOKEN, mobileToken);
        return this;
    }

    public OffersSearchRequestParamsBuilder marker(String marker) {
        this.params.put(MARKER, marker);
        return this;
    }

    public OffersSearchRequestParamsBuilder currency(String currency) {
        this.params.put(CURRENCY, currency);
        return this;
    }

    public OffersSearchRequestParamsBuilder language(String language) {
        this.params.put(LANGUAGE, language);
        return this;
    }

    public OffersSearchRequestParamsBuilder host(String host) {
        this.params.put(HOST, host);
        return this;
    }

    public OffersSearchRequestParamsBuilder discounts(int discounts) {
        this.params.put(DISCOUNTS, Integer.valueOf(discounts));
        return this;
    }

    public OffersSearchRequestParamsBuilder flagSource(String flagSource) {
        this.params.put(FLAGS_MOBILE_SOURCE, flagSource);
        return this;
    }

    public OffersSearchRequestParamsBuilder flagSection(String flagSection) {
        this.params.put(FLAGS_MOBILE_SECTION, flagSection);
        return this;
    }

    public OffersSearchRequestParamsBuilder flagFunc(String flagFunc) {
        this.params.put(FLAGS_MOBILE_FUNC, flagFunc);
        return this;
    }

    public OffersSearchRequestParamsBuilder flagBadge(String flagBadge) {
        this.params.put(FLAGS_MOBILE_BADGE, flagBadge);
        return this;
    }

    public OffersSearchRequestParamsBuilder hls(String hls) {
        this.params.put(FLAGS_UTM, hls);
        return this;
    }

    public OffersSearchRequestParamsBuilder utmDetail(String utmDetail) {
        this.params.put(FLAGS_UTM_DETAIL, utmDetail);
        return this;
    }

    public OffersSearchRequestParamsBuilder allowEn(int allowEn) {
        this.params.put(ALLOW_EN, Integer.valueOf(allowEn));
        return this;
    }

    public Map<String, Object> build() {
        this.params.put(UUID, generateRandomUUIDToAvoidUrlCaching());
        return this.params;
    }

    private String generateRandomUUIDToAvoidUrlCaching() {
        return UUID.randomUUID().toString();
    }
}
