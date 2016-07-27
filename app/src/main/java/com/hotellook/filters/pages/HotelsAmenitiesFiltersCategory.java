package com.hotellook.filters.pages;

import com.hotellook.core.api.Constants.Amenities;
import com.hotellook.core.api.pojo.hoteldetail.AmenityData;
import com.hotellook.filters.items.HotelAmenityFilterItem;

public class HotelsAmenitiesFiltersCategory extends SequentalHotelListFiltersCategory {
    private final HotelAmenityFilterItem conditioningItem;
    private final HotelAmenityFilterItem fitnessItem;
    private final HotelAmenityFilterItem internetItem;
    private final HotelAmenityFilterItem laundryItem;
    private final HotelAmenityFilterItem parkingItem;
    private final HotelAmenityFilterItem petsItem;
    private final HotelAmenityFilterItem poolItem;
    private final HotelAmenityFilterItem restaurantItem;
    private final HotelAmenityFilterItem tvItem;

    public HotelsAmenitiesFiltersCategory() {
        this.restaurantItem = new HotelAmenityFilterItem(Amenities.RESTAURANT);
        this.parkingItem = new HotelAmenityFilterItem(AmenityData.AMENITY_PARKING);
        this.conditioningItem = new HotelAmenityFilterItem(Amenities.CONDITIONING);
        this.internetItem = new HotelAmenityFilterItem(Amenities.INTERNET);
        this.poolItem = new HotelAmenityFilterItem(Amenities.POOL);
        this.fitnessItem = new HotelAmenityFilterItem(Amenities.FITNESS);
        this.petsItem = new HotelAmenityFilterItem(Amenities.PETS);
        this.tvItem = new HotelAmenityFilterItem(AmenityData.AMENITY_TV);
        this.laundryItem = new HotelAmenityFilterItem(Amenities.LAUNDARY);
        addFilter(this.restaurantItem);
        addFilter(this.parkingItem);
        addFilter(this.conditioningItem);
        addFilter(this.internetItem);
        addFilter(this.poolItem);
        addFilter(this.fitnessItem);
        addFilter(this.petsItem);
        addFilter(this.tvItem);
        addFilter(this.laundryItem);
    }

    public HotelAmenityFilterItem getRestaurantItem() {
        return this.restaurantItem;
    }

    public HotelAmenityFilterItem getParkingItem() {
        return this.parkingItem;
    }

    public HotelAmenityFilterItem getConditioningItem() {
        return this.conditioningItem;
    }

    public HotelAmenityFilterItem getInternetItem() {
        return this.internetItem;
    }

    public HotelAmenityFilterItem getPoolItem() {
        return this.poolItem;
    }

    public HotelAmenityFilterItem getFitnessItem() {
        return this.fitnessItem;
    }

    public HotelAmenityFilterItem getPetsItem() {
        return this.petsItem;
    }

    public HotelAmenityFilterItem getTvItem() {
        return this.tvItem;
    }

    public HotelAmenityFilterItem getLaundryItem() {
        return this.laundryItem;
    }
}
