package com.hotellook.statistics.mixpanel;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badge;
import com.hotellook.badges.Badges;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.Constants.HotelTypes;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.ui.screen.hotel.HotelScreenOpenInfo;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import com.hotellook.utils.DateUtils;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.zhanghai.android.materialprogressbar.BuildConfig;

public class HotelScreenParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    @Nullable
    private final Badges badges;
    private CityInfo cityInfo;
    @Nullable
    private final DiscountsByRooms discounts;
    private final MixPanelEventsKeeper eventsKeeper;
    @Nullable
    private final HighlightsByRooms highlights;
    private HotelDetailData hotelDetailData;
    private final HotelScreenOpenInfo hotelScreenOpenInfo;
    @Nullable
    private final List<Offer> prices;
    private final SearchParams searchParams;

    public HotelScreenParams(HotelScreenOpenInfo hotelScreenOpenInfo, HotelDetailData hotelDetailData, CityInfo cityInfo, @Nullable Badges badges, @Nullable List<Offer> prices, SearchParams searchParams, MixPanelEventsKeeper eventsKeeper, @Nullable DiscountsByRooms discounts, @Nullable HighlightsByRooms highlights) {
        this.hotelScreenOpenInfo = hotelScreenOpenInfo;
        this.hotelDetailData = hotelDetailData;
        this.cityInfo = cityInfo;
        this.badges = badges;
        this.prices = prices;
        this.searchParams = searchParams;
        this.eventsKeeper = eventsKeeper;
        this.discounts = discounts;
        this.highlights = highlights;
    }

    public Map<String, Object> buildParams() {
        int i = 0;
        Map<String, Object> params = new HashMap();
        long hotelId = this.hotelDetailData.getId();
        params.put(MixPanelParams.HOTEL_ID, Long.valueOf(hotelId));
        params.put(MixPanelParams.HOTEL_REFERRER, this.hotelScreenOpenInfo.source.mixpanelName);
        params.put(MixPanelParams.HOTEL_DISCOUNT, Boolean.valueOf(hasDiscount()));
        params.put(MixPanelParams.HOTEL_NAME, this.hotelDetailData.getName());
        String str = MixPanelParams.HOTEL_BADGE;
        boolean z = this.badges != null && this.badges.getBadges(hotelId).size() > 0;
        params.put(str, Boolean.valueOf(z));
        if (this.badges != null) {
            params.put(MixPanelParams.HOTEL_BADGE_LIST, getBadgeList());
        }
        params.put(MixPanelParams.HOTEL_STARS, Integer.valueOf(this.hotelDetailData.getStars()));
        params.put(MixPanelParams.HOTEL_RATING, Integer.valueOf(this.hotelDetailData.getRating()));
        params.put(MixPanelParams.HOTEL_PARTNERS, Integer.valueOf(getPartnersCount()));
        String str2 = MixPanelParams.HOTEL_OFFERS;
        if (this.prices != null) {
            i = this.prices.size();
        }
        params.put(str2, Integer.valueOf(i));
        params.put(MixPanelParams.HOTEL_CITY, Integer.valueOf(this.cityInfo.getId()));
        params.put(MixPanelParams.HOTEL_CITY_NAME, this.cityInfo.getLatinCity());
        params.put(MixPanelParams.HOTEL_COUNTRY, Integer.valueOf(this.cityInfo.getCountryId()));
        params.put(MixPanelParams.HOTEL_COUNTRY_NAME, this.cityInfo.getLatinCountry());
        if (this.searchParams != null) {
            params.put(MixPanelParams.HOTEL_DEPTH, Integer.valueOf(DateUtils.calculateDepth(this.searchParams.checkIn())));
            if (this.searchParams.checkOut() != null) {
                int nights = this.searchParams.nightsCount();
                params.put(MixPanelParams.HOTEL_NIGHTS, Integer.valueOf(nights));
                double bestPrice = getBestPrice();
                params.put(MixPanelParams.HOTEL_OFFER_PRICE, Double.valueOf(bestPrice));
                params.put(MixPanelParams.HOTEL_DAY_PRICE, Double.valueOf(bestPrice / ((double) nights)));
            }
        }
        params.put(MixPanelParams.ORIENTATION, AndroidUtils.isPortrait(HotellookApplication.getApp()) ? MixPanelParams.PORTRAIT : MixPanelParams.LANDSCAPE);
        params.put(MixPanelParams.HOTEL_POSITION, Integer.valueOf(this.hotelScreenOpenInfo.position));
        params.put(MixPanelParams.HOTEL_COUNT, Integer.valueOf(this.eventsKeeper.getHotelsShowedCount()));
        params.put(MixPanelParams.HOTEL_FAVORITE, Boolean.valueOf(new FavoritesRepository(HotellookApplication.getApp().getComponent().getDatabaseHelper()).isInFavorites(hotelId)));
        params.put(MixPanelParams.HOTEL_TYPE, HotelTypes.PROPERTY_TYPE_NAMES.get(Integer.valueOf(this.hotelDetailData.getPropertyType())));
        params.put(MixPanelParams.HOTEL_RENTALS, getRentals());
        params.put(MixPanelParams.HOTEL_PHOTOS_BEFORE, Integer.valueOf(this.eventsKeeper.getShowedImagesCount(hotelId)));
        if (hasPrices()) {
            Offer bestOffer = getBestOffer();
            if (OfferUtils.hasValidDiscount(bestOffer, this.discounts, this.highlights) || OfferUtils.hasSpecialOffer(bestOffer, this.highlights)) {
                params.put(MixPanelParams.HOTEL_HIGHLIGHTED, this.highlights.highlight(bestOffer));
            }
        }
        return params;
    }

    private double getBestPrice() {
        if (hasPrices()) {
            return getBestOffer().getPriceUsd();
        }
        return 0.0d;
    }

    private boolean hasPrices() {
        return !CollectionUtils.isEmpty(this.prices);
    }

    private Offer getBestOffer() {
        return (Offer) Collections.min(this.prices, CompareUtils.getComparatorByRoomPrice(this.discounts, this.highlights));
    }

    private String getRentals() {
        boolean hasRentals = this.hotelDetailData.hasRentals();
        if (this.hotelDetailData.isRentals()) {
            return MixPanelParams.RENTALS;
        }
        if (hasRentals) {
            return MixPanelParams.MIXED;
        }
        return MixPanelParams.HOTEL;
    }

    private int getPartnersCount() {
        if (this.prices == null) {
            return 0;
        }
        Set<Integer> gates = new HashSet();
        for (Offer price : this.prices) {
            gates.add(Integer.valueOf(price.getGateId()));
        }
        return gates.size();
    }

    private String getBadgeList() {
        if (this.badges == null) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder sb = new StringBuilder();
        String sep = BuildConfig.FLAVOR;
        for (Badge badge : this.badges.getBadges(this.hotelDetailData.getId())) {
            sb.append(sep).append(badge.name);
            sep = ", ";
        }
        return sb.toString();
    }

    private boolean hasDiscount() {
        if (this.prices != null) {
            for (Offer price : this.prices) {
                if (OfferUtils.hasValidDiscount(price, this.discounts, this.highlights)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.HOTEL_COUNT, Integer.valueOf(this.eventsKeeper.getHotelsShowedCount()));
        return params;
    }
}
