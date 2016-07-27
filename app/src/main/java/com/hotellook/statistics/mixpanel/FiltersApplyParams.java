package com.hotellook.statistics.mixpanel;

import com.hotellook.HotellookApplication;
import com.hotellook.api.exchange.CurrencyConverter;
import com.hotellook.filters.Filters;
import com.hotellook.filters.items.PaymentFilterItem;
import com.hotellook.filters.items.PriceFilterItem;
import com.hotellook.filters.pages.AgencyFiltersCategory;
import com.hotellook.filters.pages.AmenitiesFiltersCategory;
import com.hotellook.filters.pages.DistrictsFiltersCategory;
import com.hotellook.filters.pages.GeneralFiltersCategory;
import com.hotellook.filters.pages.HotelsAmenitiesFiltersCategory;
import com.hotellook.filters.pages.RoomsAmenitiesFiltersCategory;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.statistics.StatisticPrefs;
import com.hotellook.ui.screen.filters.FilterApplySource;
import com.hotellook.ui.screen.filters.FilterOpenSource;
import java.util.HashMap;
import java.util.Map;

public class FiltersApplyParams implements MixPanelParamsBuilder, MixPanelSuperParamsBuilder {
    private final MixPanelEventsKeeper eventsKeeper;
    private final FilterApplySource filterApplySource;
    private final FilterOpenSource filterOpenSource;
    private final Filters filters;
    private final StatisticPrefs prefs;

    public FiltersApplyParams(StatisticPrefs prefs, Filters filters, MixPanelEventsKeeper eventsKeeper, FilterOpenSource filterOpenSource, FilterApplySource filterApplySource) {
        this.prefs = prefs;
        this.filters = filters;
        this.eventsKeeper = eventsKeeper;
        this.filterOpenSource = filterOpenSource;
        this.filterApplySource = filterApplySource;
    }

    public Map<String, Object> buildParams() {
        boolean z;
        boolean z2 = true;
        Map<String, Object> params = new HashMap();
        addParamsFromGeneralPageForMixpanel(params, this.filters.getGeneralPage());
        AgencyFiltersCategory agencyFiltersCategory = this.filters.getAgencyPage();
        String str = MixPanelParams.FILTER_OTA;
        if (agencyFiltersCategory.inDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        DistrictsFiltersCategory districtsFiltersCategory = this.filters.getDistrictsPage();
        String str2 = MixPanelParams.FILTER_DISTRICT;
        if (districtsFiltersCategory.inDefaultState()) {
            z2 = false;
        }
        params.put(str2, Boolean.valueOf(z2));
        addParamsFromAmenities(params, this.filters.getOptionsAndServicesPage());
        params.put(MixPanelParams.FILTER_HOTELS_BEFORE, Integer.valueOf(this.eventsKeeper.getHotelsShowedCountBeforeSortingAndResetIt()));
        params.put(MixPanelParams.FILTER_REFERRER, this.filterOpenSource.mixpanelLiteral);
        params.put(MixPanelParams.FILTER_TYPE, this.filterApplySource.mixpanelLiteral);
        params.put(MixPanelParams.FILTER_COUNT, Integer.valueOf(this.prefs.getFilterCount()));
        return params;
    }

    private void addParamsFromAmenities(Map<String, Object> params, AmenitiesFiltersCategory amenitiesCategory) {
        boolean z;
        boolean z2 = true;
        RoomsAmenitiesFiltersCategory roomsPage = amenitiesCategory.getRoomsAmenitiesPage();
        HotelsAmenitiesFiltersCategory hotelsPage = amenitiesCategory.getHotelsAmenitiesPage();
        PaymentFilterItem paymentFilterItem = amenitiesCategory.getPaymentFilterItem();
        params.put(MixPanelParams.FILTER_BREAKFAST, Boolean.valueOf(!roomsPage.isBreakfastInDefaultState()));
        String str = MixPanelParams.FILTER_VIEW;
        if (roomsPage.isViewInDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        str = MixPanelParams.FILTER_SMOKING;
        if (roomsPage.isSmokingInDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        String str2 = MixPanelParams.FILTER_CANCELLATION;
        if (roomsPage.isRefundableInDefaultState()) {
            z2 = false;
        }
        params.put(str2, Boolean.valueOf(z2));
        params.put(MixPanelParams.FILTER_BANK_CARD, Boolean.valueOf(paymentFilterItem.isCardNotRequired()));
        params.put(MixPanelParams.FILTER_PAY_NOW, Boolean.valueOf(paymentFilterItem.isPayNow()));
        params.put(MixPanelParams.FILTER_PAY_LATER, Boolean.valueOf(paymentFilterItem.isPayLater()));
        params.put(MixPanelParams.FILTER_ROOMS_AMENITIES, Boolean.valueOf(isRoomAmenitiesEnabled(hotelsPage)));
        params.put(MixPanelParams.FILTER_HOTEL_AMENITIES, Boolean.valueOf(isHotelAmenitiesEnabled(hotelsPage)));
    }

    private boolean isHotelAmenitiesEnabled(HotelsAmenitiesFiltersCategory hotelsPage) {
        if (hotelsPage.getParkingItem().inDefaultState() && hotelsPage.getRestaurantItem().inDefaultState() && hotelsPage.getInternetItem().inDefaultState() && hotelsPage.getPoolItem().inDefaultState() && hotelsPage.getFitnessItem().inDefaultState() && hotelsPage.getPetsItem().inDefaultState() && hotelsPage.getLaundryItem().inDefaultState()) {
            return false;
        }
        return true;
    }

    private boolean isRoomAmenitiesEnabled(HotelsAmenitiesFiltersCategory hotelsPage) {
        return (hotelsPage.getConditioningItem().inDefaultState() && hotelsPage.getTvItem().inDefaultState()) ? false : true;
    }

    private void addParamsFromGeneralPageForMixpanel(Map<String, Object> params, GeneralFiltersCategory page) {
        boolean z;
        boolean z2 = true;
        params.put(MixPanelParams.FILTER_PRICE, Boolean.valueOf(!page.isPriceInDefaultState()));
        addPricesParams(params, page);
        String str = MixPanelParams.FILTER_DISTANCE;
        if (page.isDistanceInDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        str = MixPanelParams.FILTER_STARS;
        if (page.isStarsInDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        params.put(MixPanelParams.FILTER_RATING, Integer.valueOf(page.getRating()));
        str = MixPanelParams.FILTER_NO_ROOMS;
        if (page.isNoRoomsInDefaultState()) {
            z = false;
        } else {
            z = true;
        }
        params.put(str, Boolean.valueOf(z));
        String str2 = MixPanelParams.FILTER_HOSTEL;
        if (page.isHostelsAndGuesthousesInDefaultState()) {
            z2 = false;
        }
        params.put(str2, Boolean.valueOf(z2));
    }

    private void addPricesParams(Map<String, Object> params, GeneralFiltersCategory page) {
        CurrencyConverter currencyConverter = HotellookApplication.getApp().getComponent().getCurrencyConverter();
        PriceFilterItem priceFilterItem = page.getPriceFilterItem();
        double priceMinInUsd = currencyConverter.convert(priceFilterItem.getMinInterpolatedValue());
        double priceMaxInUsd = currencyConverter.convert(priceFilterItem.getMaxInterpolatedValue());
        params.put(MixPanelParams.FILTER_PRICE_MIN, Double.valueOf(priceMinInUsd));
        params.put(MixPanelParams.FILTER_PRICE_MAX, Double.valueOf(priceMaxInUsd));
    }

    public Map<String, Object> buildSuperParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.FILTER_COUNT, Integer.valueOf(this.prefs.getFilterCount()));
        return params;
    }
}
