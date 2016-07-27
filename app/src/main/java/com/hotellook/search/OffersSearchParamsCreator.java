package com.hotellook.search;

import com.hotellook.BuildConfig;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.core.api.params.OffersSearchRequestParamsBuilder;
import com.hotellook.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OffersSearchParamsCreator {
    private static final int LOAD_DISCOUNTS = 1;

    public static Map<String, Object> create(SearchParams searchParams, RequestFlags requestFlags, List<Integer> locationIds, String token) {
        return toBuilder(searchParams, requestFlags, locationIds, token, null).build();
    }

    private static OffersSearchRequestParamsBuilder toBuilder(SearchParams searchParams, RequestFlags requestFlags, List<Integer> locationIds, String token, Set<Integer> gates) {
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        OffersSearchRequestParamsBuilder offersRequestBuilder = new OffersSearchRequestParamsBuilder().locationIds(locationIds).checkIn(dateFormatter.format(searchParams.checkIn())).checkOut(dateFormatter.format(searchParams.checkOut())).adults(searchParams.adults()).children(KidsSerializer.toString(searchParams.kids())).allowEn(convertAllowEnGateToServerFormat(searchParams.areEnGatesAllowed())).currency(searchParams.currency()).language(searchParams.language()).discounts(LOAD_DISCOUNTS).flagBadge(requestFlags.getBadge()).flagFunc(requestFlags.getFunc()).flagSection(requestFlags.getSection()).flagSource(requestFlags.getSource()).host(HotellookApplication.getApp().getHost()).marker(BuildConfig.GOOGLE_PLAY_BUILD.booleanValue() ? requestFlags.getMarker() : BuildConfig.STORE_MARKER).mobileToken(token).hls(requestFlags.getHls()).utmDetail(requestFlags.getUtmDetail());
        if (CollectionUtils.isNotEmpty(gates)) {
            offersRequestBuilder.receivedGates(new ArrayList(gates));
        }
        if (searchParams.hasHotelId()) {
            offersRequestBuilder.hotelId(searchParams.hotelId());
        }
        return offersRequestBuilder;
    }

    public static Map<String, Object> create(SearchParams searchParams, RequestFlags requestFlags, List<Integer> locationIds, String token, Set<Integer> gates) {
        return toBuilder(searchParams, requestFlags, locationIds, token, gates).build();
    }

    private static int convertAllowEnGateToServerFormat(boolean areEnGatesAllowed) {
        return areEnGatesAllowed ? LOAD_DISCOUNTS : 0;
    }
}
