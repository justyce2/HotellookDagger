package com.hotellook.events;

import android.support.annotation.Nullable;
import com.hotellook.badges.Badges;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import java.util.List;

public class HotelFragmentCreatedEvent {
    public final Badges badges;
    @Nullable
    public final DiscountsByRooms discounts;
    @Nullable
    public final HighlightsByRooms highlights;
    public final long hotelId;
    public HotelScreenOpenInfo openInfo;
    public final List<Offer> prices;
    public final SearchParams searchParams;

    public HotelFragmentCreatedEvent(HotelScreenOpenInfo hotelScreenOpenInfo, long hotelId, Badges badges, List<Offer> prices, SearchParams searchParams, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        this.openInfo = hotelScreenOpenInfo;
        this.hotelId = hotelId;
        this.badges = badges;
        this.prices = prices;
        this.searchParams = searchParams;
        this.discounts = discounts;
        this.highlights = highlights;
    }
}
