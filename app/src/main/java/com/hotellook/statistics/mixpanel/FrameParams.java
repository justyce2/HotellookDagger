package com.hotellook.statistics.mixpanel;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.badges.Badges;
import com.hotellook.common.OfferUtils;
import com.hotellook.core.api.Constants.HotelTypes;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.filters.Filters;
import com.hotellook.search.Discounts;
import com.hotellook.search.Highlights;
import com.hotellook.search.SearchParams;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.statistics.StatisticPrefs;
import com.hotellook.ui.screen.hotel.FrameOpenSource;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.DateUtils;
import java.util.HashMap;
import java.util.Map;
import pl.charmas.android.reactivelocation.C1822R;

public class FrameParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    @Nullable
    private final Badges badges;
    private final BasicHotelData basicHotelData;
    private final Offer bestPrice;
    private final Discounts discounts;
    private final MixPanelEventsKeeper eventsKeeper;
    @Nullable
    private final Filters filters;
    private int framesCount;
    private final Highlights highlights;
    private final HotelDetailData hotelDetailData;
    private final FrameOpenSource openSource;
    private final Offer roomData;
    private final SearchParams searchParams;
    private final int tabId;

    public FrameParams(BasicHotelData basicHotelData, HotelDetailData hotelDetailData, @Nullable Filters filters, @Nullable Badges badges, Offer roomData, Offer bestPrice, SearchParams searchParams, MixPanelEventsKeeper eventsKeeper, FrameOpenSource openSource, int tabId, Discounts discounts, Highlights highlights) {
        this.hotelDetailData = hotelDetailData;
        this.basicHotelData = basicHotelData;
        this.filters = filters;
        this.badges = badges;
        this.roomData = roomData;
        this.bestPrice = bestPrice;
        this.searchParams = searchParams;
        this.eventsKeeper = eventsKeeper;
        this.openSource = openSource;
        this.tabId = tabId;
        this.discounts = discounts;
        this.highlights = highlights;
        this.framesCount = new StatisticPrefs(HotellookApplication.getApp()).incFramesAndGet();
    }

    public Map<String, Object> buildParams() {
        boolean z = false;
        Map<String, Object> params = new HashMap();
        long hotelId = this.basicHotelData.id();
        params.put(MixPanelParams.FRAME_HOTEL_ID, Long.valueOf(hotelId));
        params.put(MixPanelParams.FRAME_GATE_ID, Integer.valueOf(this.roomData.getGateId()));
        params.put(MixPanelParams.FRAME_ROOM_ID, Integer.valueOf(this.roomData.getRoomId()));
        params.put(MixPanelParams.FRAME_PARTNER, this.roomData.getGateName());
        if (this.badges != null) {
            boolean z2;
            String str = MixPanelParams.FRAME_BADGE;
            if (this.badges.getBadges(hotelId).size() > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            params.put(str, Boolean.valueOf(z2));
        }
        params.put(MixPanelParams.FRAME_DISCOUNT, Boolean.valueOf(OfferUtils.hasValidDiscount(this.roomData, this.discounts.get(hotelId), this.highlights.get(hotelId))));
        params.put(MixPanelParams.FRAME_PRIVATE_FARE, Boolean.valueOf(OfferUtils.hasSpecialOffer(this.roomData, this.highlights.get(hotelId))));
        String str2 = MixPanelParams.FRAME_OFFER_HIGHLIGHTED;
        if (OfferUtils.hasValidDiscount(this.roomData, this.discounts.get(hotelId), this.highlights.get(hotelId)) || OfferUtils.hasSpecialOffer(this.roomData, this.highlights.get(hotelId))) {
            z = true;
        }
        params.put(str2, Boolean.valueOf(z));
        params.put(MixPanelParams.FRAME_STARS, Integer.valueOf(this.basicHotelData.stars()));
        params.put(MixPanelParams.FRAME_RATING, Integer.valueOf(this.basicHotelData.rating()));
        params.put(MixPanelParams.FRAME_CITY, Integer.valueOf(this.searchParams.locationId()));
        params.put(MixPanelParams.FRAME_DEPTH, Integer.valueOf(DateUtils.calculateDepth(this.searchParams.checkIn())));
        double price = this.roomData.getPriceUsd();
        int nights = this.searchParams.nightsCount();
        params.put(MixPanelParams.FRAME_DAY_PRICE, Double.valueOf(price / ((double) nights)));
        params.put(MixPanelParams.FRAME_OFFER_PRICE, Double.valueOf(price));
        params.put(MixPanelParams.FRAME_LENGTH, Integer.valueOf(nights));
        params.put(MixPanelParams.FRAME_FAVOURITE, Boolean.valueOf(new FavoritesRepository(HotellookApplication.getApp().getComponent().getDatabaseHelper()).isInFavorites(hotelId)));
        params.put(MixPanelParams.ORIENTATION, Boolean.valueOf(AndroidUtils.isPortrait(HotellookApplication.getApp())));
        params.put(MixPanelParams.FRAME_REVIEWS_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(hotelId, 13)));
        params.put(MixPanelParams.FRAME_RATES_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(hotelId, 11)));
        params.put(MixPanelParams.FRAME_RATING_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(hotelId, 12)));
        params.put(MixPanelParams.FRAME_COUNT, Integer.valueOf(this.framesCount));
        if (this.filters != null) {
            params.put(MixPanelParams.FRAME_SORTED_TYPE, new SortingTypeConverter(this.filters.getSortingAlgorithmId(), this.filters.getSortingCategory().getSortingItem().getDistanceTarget()).convert());
        }
        params.put(MixPanelParams.FRAME_FILTERED, Boolean.valueOf(this.eventsKeeper.isActionMet(-1, 15)));
        LaunchSource launchReferrer = this.eventsKeeper.getLaunchReferrer();
        params.put(MixPanelParams.FRAME_LAUNCH_REFERRER, launchReferrer == null ? "untracked" : launchReferrer.toString());
        params.put(MixPanelParams.FRAME_SEARCH_REFERRER, this.eventsKeeper.getSearchSource().mixpanelLiteral);
        params.put(MixPanelParams.FRAME_HOTEL_REFERRER, this.eventsKeeper.getHotelOpenedSource(hotelId));
        params.put(MixPanelParams.FRAME_TAB_REFRERRER, getTabName());
        if (this.hotelDetailData != null) {
            params.put(MixPanelParams.FRAME_HOTEL_TYPE, HotelTypes.PROPERTY_TYPE_NAMES.get(Integer.valueOf(this.hotelDetailData.getPropertyType())));
            params.put(MixPanelParams.FRAME_RENTALS, getRentals(this.hotelDetailData));
        }
        params.put(MixPanelParams.FRAME_RECOMMENDED, Boolean.valueOf(isRecommendedOffer()));
        params.put(MixPanelParams.FRAME_REFERRER, this.openSource.mixpanelLiteral);
        params.put(MixPanelParams.FRAME_MAP_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(hotelId, 7)));
        params.put(MixPanelParams.FRAME_PHOTOS, Integer.valueOf(this.eventsKeeper.getShowedImagesCount(hotelId)));
        return params;
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.FRAME_COUNT, Integer.valueOf(this.framesCount));
        return params;
    }

    private boolean isRecommendedOffer() {
        return this.bestPrice != null && this.bestPrice.getGateId() == this.roomData.getGateId() && this.bestPrice.getRoomId() == this.bestPrice.getRoomId();
    }

    private String getRentals(HotelDetailData hotelDetailData) {
        boolean hasRentals = hotelDetailData.hasRentals();
        if (hotelDetailData.isRentals()) {
            return MixPanelParams.RENTALS;
        }
        if (hasRentals) {
            return MixPanelParams.MIXED;
        }
        return MixPanelParams.HOTEL;
    }

    private String getTabName() {
        switch (this.tabId) {
            case C1822R.styleable.MapAttrs_uiTiltGestures /*10*/:
                return MixPanelParams.INFO;
            case C1822R.styleable.MapAttrs_uiZoomControls /*11*/:
                return MixPanelParams.RATES;
            case C1822R.styleable.MapAttrs_uiZoomGestures /*12*/:
                return MixPanelParams.RATING;
            case C1822R.styleable.MapAttrs_useViewLifecycle /*13*/:
                return MixPanelParams.REVIEWS;
            default:
                return SortingChangedParams.UNKNOWN;
        }
    }
}
