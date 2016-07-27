package com.hotellook.ui.screen.hotel.data;

import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.db.data.FavoriteHotelDataEntity;

public class BasicHotelData {
    private final String address;
    private final boolean hasFoursquareTips;
    private final boolean hasTrustYouData;
    private final long id;
    private final Coordinates location;
    private final String name;
    private final int photoCount;
    private final String propertyTypeName;
    private final int rating;
    private final int stars;

    public BasicHotelData(HotelDetailData hotelDetailData) {
        boolean z = true;
        this.id = hotelDetailData.getId();
        this.name = hotelDetailData.getName();
        this.photoCount = hotelDetailData.getPhotoCount();
        this.hasTrustYouData = hotelDetailData.getTrustyou() != null;
        if (hotelDetailData.getFoursquare() == null) {
            z = false;
        }
        this.hasFoursquareTips = z;
        this.address = hotelDetailData.getAddress();
        this.location = hotelDetailData.getLocation();
        this.rating = hotelDetailData.getRating();
        this.stars = hotelDetailData.getStars();
        this.propertyTypeName = hotelDetailData.getPropertyTypeName();
    }

    public BasicHotelData(HotelData hotelData) {
        this.id = hotelData.getId();
        this.name = hotelData.getName();
        this.photoCount = hotelData.getPhotoCount();
        this.hasTrustYouData = hotelData.getHasTrustYouData();
        this.hasFoursquareTips = hotelData.getHasFoursquareTips();
        this.address = hotelData.getAddress();
        this.location = hotelData.getLocation();
        this.rating = hotelData.getRating();
        this.stars = hotelData.getStars();
        this.propertyTypeName = hotelData.getPropertyTypeName();
    }

    public BasicHotelData(FavoriteHotelDataEntity favoriteHotelData) {
        this.id = favoriteHotelData.getHotelId();
        this.name = favoriteHotelData.getHotelName();
        this.photoCount = favoriteHotelData.getPhotoCount();
        this.hasTrustYouData = favoriteHotelData.hasTrustYouData();
        this.hasFoursquareTips = favoriteHotelData.hasFoursquareTips();
        this.address = favoriteHotelData.getHotelAddress();
        this.location = favoriteHotelData.getLocation();
        this.rating = favoriteHotelData.getRating();
        this.stars = favoriteHotelData.getStars();
        this.propertyTypeName = null;
    }

    public long id() {
        return this.id;
    }

    public int photoCount() {
        return this.photoCount;
    }

    public boolean hasRatingsData() {
        return this.hasTrustYouData;
    }

    public boolean hasFoursquareTips() {
        return this.hasFoursquareTips;
    }

    public String address() {
        return this.address;
    }

    public Coordinates location() {
        return this.location;
    }

    public String name() {
        return this.name;
    }

    public int rating() {
        return this.rating;
    }

    public int stars() {
        return this.stars;
    }

    public String propertyTypeName() {
        return this.propertyTypeName;
    }
}
