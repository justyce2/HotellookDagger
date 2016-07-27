package com.hotellook.search;

import android.support.annotation.NonNull;
import com.hotellook.api.RequestFlags;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.List;
import java.util.Map;

public class SearchData {
    @NonNull
    private final Discounts discounts;
    @NonNull
    private final Districts districts;
    @NonNull
    private final Highlights highlights;
    @NonNull
    private final Hotels hotels;
    @NonNull
    private final Locations locations;
    private final boolean nearbyCitiesAvailable;
    @NonNull
    private final Offers offers;
    @NonNull
    private final Pois pois;
    @NonNull
    private final RequestFlags requestFlags;
    @NonNull
    private final RoomTypes roomTypes;
    @NonNull
    private final SearchParams searchParams;
    @NonNull
    private final Seasons seasons;

    public SearchData(@NonNull SearchParams searchParams, @NonNull RequestFlags requestFlags, @NonNull Locations locations, @NonNull Hotels hotels, @NonNull Offers offers, @NonNull RoomTypes roomTypes, @NonNull Pois pois, @NonNull Seasons seasons, @NonNull Districts districts, boolean nearbyCitiesAvailable, @NonNull Discounts discounts, @NonNull Highlights highlights) {
        this.searchParams = searchParams;
        this.requestFlags = requestFlags;
        this.locations = locations;
        this.hotels = hotels;
        this.offers = offers;
        this.roomTypes = roomTypes;
        this.pois = pois;
        this.seasons = seasons;
        this.districts = districts;
        this.discounts = discounts;
        this.highlights = highlights;
        this.nearbyCitiesAvailable = nearbyCitiesAvailable;
    }

    public SearchData(@NonNull Map<Integer, List<HotelData>> hotelsInLocations, @NonNull Map<Long, List<Offer>> offers, @NonNull SearchData searchData) {
        this.searchParams = searchData.searchParams();
        this.requestFlags = searchData.requestFlags();
        this.locations = searchData.locations();
        this.hotels = new Hotels(hotelsInLocations);
        this.offers = new Offers(offers);
        this.roomTypes = searchData.roomTypes();
        this.pois = searchData.pois();
        this.seasons = searchData.seasons();
        this.districts = searchData.districts();
        this.discounts = searchData.discounts();
        this.highlights = searchData.highlights();
        this.nearbyCitiesAvailable = searchData.nearbyCitiesAvailable();
    }

    @NonNull
    public SearchParams searchParams() {
        return this.searchParams;
    }

    @NonNull
    public RequestFlags requestFlags() {
        return this.requestFlags;
    }

    @NonNull
    public Locations locations() {
        return this.locations;
    }

    @NonNull
    public Hotels hotels() {
        return this.hotels;
    }

    @NonNull
    public Offers offers() {
        return this.offers;
    }

    @NonNull
    public RoomTypes roomTypes() {
        return this.roomTypes;
    }

    @NonNull
    public Pois pois() {
        return this.pois;
    }

    @NonNull
    public Seasons seasons() {
        return this.seasons;
    }

    @NonNull
    public Districts districts() {
        return this.districts;
    }

    @NonNull
    public Discounts discounts() {
        return this.discounts;
    }

    @NonNull
    public Highlights highlights() {
        return this.highlights;
    }

    public boolean nearbyCitiesAvailable() {
        return this.nearbyCitiesAvailable;
    }
}
