package com.hotellook.events;

import android.support.annotation.Nullable;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.Filters;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.flurry.HotelScreenUserActions;
import com.hotellook.ui.screen.hotel.FrameOpenSource;
import com.hotellook.ui.screen.hotel.Source;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;

public class PurchaseEvent {
    @Nullable
    public final Badges badges;
    public final BasicHotelData basicHotelData;
    public final Offer bestPrice;
    public final Discounts discounts;
    @Nullable
    public final Filters filters;
    public final Highlights highlights;
    @Nullable
    public final HotelDetailData hotelDetailData;
    public final FrameOpenSource openSource;
    public final Offer roomData;
    public final SearchParams searchParams;
    public final Source source;
    public final int tabSourceId;
    public final HotelScreenUserActions userActions;

    public PurchaseEvent(BasicHotelData basicHotelData, HotelDetailData hotelDetailData, HotelScreenUserActions userActions, SearchParams searchParams, Offer roomData, Offer bestPrice, FrameOpenSource openSource, Source source, int tabSource, Discounts discounts, Highlights highlights, Filters filters, Badges badges) {
        this.basicHotelData = basicHotelData;
        this.hotelDetailData = hotelDetailData;
        this.userActions = userActions;
        this.searchParams = searchParams;
        this.roomData = roomData;
        this.bestPrice = bestPrice;
        this.openSource = openSource;
        this.source = source;
        this.tabSourceId = tabSource;
        this.discounts = discounts;
        this.highlights = highlights;
        this.badges = badges;
        this.filters = filters;
    }
}
