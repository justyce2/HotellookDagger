package com.hotellook.ui.screen.hotel.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.deeplink.DeeplinkData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;

public class Booking {
    @NonNull
    private final DeeplinkData deeplinkData;
    private final long hotelId;
    @NonNull
    private final Offer offer;
    @NonNull
    private final SearchParams searchParams;

    public Booking(@NonNull DeeplinkData deeplinkData, @NonNull Offer offer, @NonNull SearchParams searchParams, long hotelId) {
        this.deeplinkData = deeplinkData;
        this.offer = offer;
        this.searchParams = searchParams;
        this.hotelId = hotelId;
    }

    @Nullable
    public String deeplink() {
        return this.deeplinkData.getDeeplink();
    }

    @Nullable
    public String gateName() {
        return this.deeplinkData.getGateName();
    }

    @NonNull
    public SearchParams searchParams() {
        return this.searchParams;
    }

    @NonNull
    public Offer offer() {
        return this.offer;
    }

    public long hotelId() {
        return this.hotelId;
    }
}
