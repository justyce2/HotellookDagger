package com.hotellook.ui.screen.filters.presenters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.pages.AmenitiesFiltersCategory;
import com.hotellook.filters.pages.HotelsAmenitiesFiltersCategory;
import com.hotellook.filters.pages.RoomsAmenitiesFiltersCategory;
import com.hotellook.ui.screen.filters.presenters.OptionServicePresenter.Builder;
import java.util.ArrayList;
import java.util.List;
import me.zhanghai.android.materialprogressbar.C1759R;

public class OptionsAndServicesPresenter implements FilterPresenter {
    private final FilterPresenter cardNotRequiredPresenter;
    private final FiltersListPresenter hotelServicesPresenter;
    private final FilterPresenter payLaterFilterPresenter;
    private final FilterPresenter payNowFilterPresenter;
    private final FiltersListPresenter paymentOptionsPresenter;
    private final FiltersListPresenter roomOptionsPresenter;
    private final FiltersListPresenter roomsServicesPresenter;

    public OptionsAndServicesPresenter(AmenitiesFiltersCategory optionsAndServicesPage) {
        this.roomOptionsPresenter = createRoomOptionsPresenter(optionsAndServicesPage.getRoomsAmenitiesPage());
        this.paymentOptionsPresenter = createPaymentOptionsPresenter(optionsAndServicesPage.getRoomsAmenitiesPage());
        this.roomsServicesPresenter = createRoomsServicesPresenter(optionsAndServicesPage.getHotelsAmenitiesPage());
        this.hotelServicesPresenter = createHotelServicesPresenter(optionsAndServicesPage.getHotelsAmenitiesPage());
        this.payNowFilterPresenter = new PayNowFilterPresenter(optionsAndServicesPage.getPaymentFilterItem());
        this.payLaterFilterPresenter = new PayLaterFilterPresenter(optionsAndServicesPage.getPaymentFilterItem());
        this.cardNotRequiredPresenter = new CardNotRequiredPresenter(optionsAndServicesPage.getPaymentFilterItem());
    }

    private FiltersListPresenter createRoomOptionsPresenter(RoomsAmenitiesFiltersCategory optionsPage) {
        List<FilterPresenter> presenters = new ArrayList();
        presenters.add(new Builder().filterItem(optionsPage.getBreakfastItem()).iconId(C1178R.drawable.ic_breakfast).textId(C1178R.string.filter_breakfast).build());
        presenters.add(new Builder().filterItem(optionsPage.getViewItem()).iconId(C1178R.drawable.ic_view).textId(C1178R.string.filter_view).build());
        presenters.add(new Builder().filterItem(optionsPage.getSmokingItem()).iconId(C1178R.drawable.ic_amenity_smoking).textId(C1178R.string.filter_smoking).build());
        return new FiltersListPresenter(presenters);
    }

    private FiltersListPresenter createPaymentOptionsPresenter(RoomsAmenitiesFiltersCategory optionsPage) {
        List<FilterPresenter> presenters = new ArrayList();
        presenters.add(new Builder().filterItem(optionsPage.getRefundableItem()).iconId(C1178R.drawable.ic_refound).textId(C1178R.string.filter_refounding).build());
        return new FiltersListPresenter(presenters);
    }

    private FiltersListPresenter createRoomsServicesPresenter(HotelsAmenitiesFiltersCategory servicesPage) {
        List<FilterPresenter> presenters = new ArrayList();
        presenters.add(new Builder().filterItem(servicesPage.getConditioningItem()).iconId(C1178R.drawable.ic_amenity_air_conditioning).textId(C1178R.string.filter_condition).build());
        presenters.add(new Builder().filterItem(servicesPage.getTvItem()).iconId(C1178R.drawable.ic_amenity_tv).textId(C1178R.string.filter_tv).build());
        return new FiltersListPresenter(presenters);
    }

    private FiltersListPresenter createHotelServicesPresenter(HotelsAmenitiesFiltersCategory servicesPage) {
        List<FilterPresenter> presenters = new ArrayList();
        presenters.add(new Builder().filterItem(servicesPage.getParkingItem()).iconId(C1178R.drawable.ic_amenity_parking).textId(C1178R.string.filter_parking).build());
        presenters.add(new Builder().filterItem(servicesPage.getRestaurantItem()).iconId(C1178R.drawable.ic_amenity_restaurant).textId(C1178R.string.filter_restaurant).build());
        presenters.add(new Builder().filterItem(servicesPage.getInternetItem()).iconId(C1178R.drawable.ic_amenity_wifi).textId(C1178R.string.filter_internet).build());
        presenters.add(new Builder().filterItem(servicesPage.getPoolItem()).iconId(C1178R.drawable.ic_amenity_pool).textId(C1178R.string.filter_pool).build());
        presenters.add(new Builder().filterItem(servicesPage.getFitnessItem()).iconId(C1178R.drawable.ic_amenity_gym).textId(C1178R.string.filter_fitness).build());
        presenters.add(new Builder().filterItem(servicesPage.getPetsItem()).iconId(C1178R.drawable.ic_amenity_pets).textId(C1178R.string.filter_pets).build());
        presenters.add(new Builder().filterItem(servicesPage.getLaundryItem()).iconId(C1178R.drawable.ic_amenity_laundry).textId(C1178R.string.filter_laundry).build());
        return new FiltersListPresenter(presenters);
    }

    public void onFiltersUpdated() {
        this.roomOptionsPresenter.onFiltersUpdated();
        this.paymentOptionsPresenter.onFiltersUpdated();
        this.roomsServicesPresenter.onFiltersUpdated();
        this.hotelServicesPresenter.onFiltersUpdated();
        this.payNowFilterPresenter.onFiltersUpdated();
        this.payLaterFilterPresenter.onFiltersUpdated();
        this.cardNotRequiredPresenter.onFiltersUpdated();
    }

    public void addView(LayoutInflater inflater, ViewGroup container) {
        Context context = container.getContext();
        addTitle(inflater, container, context.getString(C1178R.string.room_options));
        this.roomOptionsPresenter.addView(inflater, container);
        addTitle(inflater, container, context.getString(C1178R.string.payment_options));
        this.paymentOptionsPresenter.addView(inflater, container);
        this.cardNotRequiredPresenter.addView(inflater, container);
        this.payNowFilterPresenter.addView(inflater, container);
        this.payLaterFilterPresenter.addView(inflater, container);
        addTitle(inflater, container, context.getString(C1178R.string.rooms_amenities));
        this.roomsServicesPresenter.addView(inflater, container);
        addTitle(inflater, container, context.getString(C1178R.string.hotel_amenities));
        this.hotelServicesPresenter.addView(inflater, container);
    }

    private void addTitle(LayoutInflater inflater, ViewGroup container, String title) {
        View v = inflater.inflate(C1178R.layout.filter_title, container, false);
        ((TextView) v.findViewById(C1759R.id.title)).setText(title);
        container.addView(v);
    }
}
