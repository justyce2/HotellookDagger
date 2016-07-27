package com.hotellook.core.api.params;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeepLinkParamsBuilder {
    private static final String ADULTS = "adults";
    private static final String CHECK_IN = "checkIn";
    private static final String CHECK_OUT = "checkOut";
    private static final String CHILDREN = "children";
    private static final String CURRENCY = "currency";
    private static final String FLAGS_MOBILE_BADGE = "flags[mobile_badge]";
    private static final String FLAGS_MOBILE_FUNC = "flags[mobile_func]";
    private static final String FLAGS_MOBILE_SECTION = "flags[mobile_section]";
    private static final String FLAGS_MOBILE_SOURCE = "flags[mobile_source]";
    private static final String FLAGS_UTM = "flags[utm]";
    private static final String FLAGS_UTM_DETAIL = "flags[utmDetail]";
    private static final String GATE_ID = "gateId";
    private static final String HOST = "host";
    private static final String HOTEL_ID = "hotelId";
    private static final String LANGUAGE = "language";
    private static final String LOCATION_ID = "locationId";
    private static final String MARKER = "marker";
    private static final String MOBILE_TOKEN = "mobileToken";
    private static final String ROOM_ID = "roomId";
    private static final String SEARCH_UUID = "searchUuid";
    private Map<String, Object> params;

    public DeepLinkParamsBuilder() {
        this.params = new HashMap();
    }

    public DeepLinkParamsBuilder locationId(int locationId) {
        this.params.put(LOCATION_ID, Integer.valueOf(locationId));
        return this;
    }

    public DeepLinkParamsBuilder locationIds(List<Integer> ids) {
        for (int i = 0; i < ids.size(); i++) {
            this.params.put(String.format(OffersSearchRequestParamsBuilder.LOCATION_IDS_FORMAT, new Object[]{Integer.valueOf(i)}), ids.get(i));
        }
        return this;
    }

    public DeepLinkParamsBuilder checkIn(String checkIn) {
        this.params.put(CHECK_IN, checkIn);
        return this;
    }

    public DeepLinkParamsBuilder checkOut(String checkOut) {
        this.params.put(CHECK_OUT, checkOut);
        return this;
    }

    public DeepLinkParamsBuilder adults(int adults) {
        this.params.put(ADULTS, Integer.valueOf(adults));
        return this;
    }

    public DeepLinkParamsBuilder children(String children) {
        this.params.put(CHILDREN, children);
        return this;
    }

    public DeepLinkParamsBuilder mobileToken(String mobileToken) {
        this.params.put(MOBILE_TOKEN, mobileToken);
        return this;
    }

    public DeepLinkParamsBuilder marker(String marker) {
        this.params.put(MARKER, marker);
        return this;
    }

    public DeepLinkParamsBuilder currency(String currency) {
        this.params.put(CURRENCY, currency);
        return this;
    }

    public DeepLinkParamsBuilder language(String language) {
        this.params.put(LANGUAGE, language);
        return this;
    }

    public DeepLinkParamsBuilder hotelId(long hotelId) {
        this.params.put(HOTEL_ID, Long.valueOf(hotelId));
        return this;
    }

    public DeepLinkParamsBuilder gateId(int gateId) {
        this.params.put(GATE_ID, Integer.valueOf(gateId));
        return this;
    }

    public DeepLinkParamsBuilder roomId(int roomId) {
        this.params.put(ROOM_ID, Integer.valueOf(roomId));
        return this;
    }

    public DeepLinkParamsBuilder host(String host) {
        this.params.put(HOST, host);
        return this;
    }

    public DeepLinkParamsBuilder flagSource(String flagSource) {
        this.params.put(FLAGS_MOBILE_SOURCE, flagSource);
        return this;
    }

    public DeepLinkParamsBuilder flagSection(String flagSection) {
        this.params.put(FLAGS_MOBILE_SECTION, flagSection);
        return this;
    }

    public DeepLinkParamsBuilder flagFunc(String flagFunc) {
        this.params.put(FLAGS_MOBILE_FUNC, flagFunc);
        return this;
    }

    public DeepLinkParamsBuilder flagBadge(String flagBadge) {
        this.params.put(FLAGS_MOBILE_BADGE, flagBadge);
        return this;
    }

    public DeepLinkParamsBuilder hls(String hls) {
        this.params.put(FLAGS_UTM, hls);
        return this;
    }

    public DeepLinkParamsBuilder utmDetail(String utmDetail) {
        this.params.put(FLAGS_UTM_DETAIL, utmDetail);
        return this;
    }

    public DeepLinkParamsBuilder searchUuid(String searchUuid) {
        this.params.put(SEARCH_UUID, searchUuid);
        return this;
    }

    public Map<String, Object> build() {
        return this.params;
    }
}
