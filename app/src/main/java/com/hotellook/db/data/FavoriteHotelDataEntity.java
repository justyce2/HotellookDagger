package com.hotellook.db.data;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.search.KidsSerializer;
import com.hotellook.search.SearchParams;
import com.hotellook.search.ServerDateFormatter;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class FavoriteHotelDataEntity {
    @DatabaseField
    private int adults;
    @DatabaseField
    private String checkIn;
    @DatabaseField
    private String checkOut;
    @DatabaseField
    private String children;
    @DatabaseField
    private String countryName;
    @DatabaseField
    private boolean hasFoursquareTips;
    @DatabaseField
    private boolean hasTrustYouData;
    @DatabaseField
    private String hotelAddress;
    @DatabaseField(id = true)
    private long hotelId;
    @DatabaseField
    private String hotelName;
    @DatabaseField
    private double latitude;
    @DatabaseField
    private int locationId;
    @DatabaseField
    private String locationName;
    @DatabaseField
    private double longitude;
    @DatabaseField
    private int nights;
    @DatabaseField
    private int photoCount;
    @DatabaseField
    private int rating;
    @DatabaseField
    private int stars;

    public FavoriteHotelDataEntity(BasicHotelData basicHotelData, CityInfo cityInfo, SearchParams searchParams) {
        fillHotelFields(basicHotelData);
        fillCityFields(cityInfo);
        fillSearchParamFields(searchParams);
    }

    public FavoriteHotelDataEntity(BasicHotelData basicHotelData, CityInfo cityInfo) {
        fillHotelFields(basicHotelData);
        fillCityFields(cityInfo);
    }

    public FavoriteHotelDataEntity(HotelData hotelData, CityInfo cityInfo, SearchParams searchParams) {
        fillHotelFields(new BasicHotelData(hotelData));
        fillCityFields(cityInfo);
        fillSearchParamFields(searchParams);
    }

    private void fillHotelFields(BasicHotelData basicHotelData) {
        this.hotelId = basicHotelData.id();
        this.hotelName = basicHotelData.name();
        this.photoCount = basicHotelData.photoCount();
        this.hotelAddress = basicHotelData.address();
        this.longitude = basicHotelData.location().getLon();
        this.latitude = basicHotelData.location().getLat();
        this.hasTrustYouData = basicHotelData.hasRatingsData();
        this.hasFoursquareTips = basicHotelData.hasFoursquareTips();
        this.rating = basicHotelData.rating();
        this.stars = basicHotelData.stars();
    }

    private void fillCityFields(CityInfo cityInfo) {
        this.locationId = cityInfo.getId();
        this.locationName = cityInfo.getCity();
        this.countryName = cityInfo.getCountry();
    }

    private void fillSearchParamFields(SearchParams searchParams) {
        ServerDateFormatter dateFormatter = new ServerDateFormatter();
        this.checkIn = dateFormatter.format(searchParams.checkIn());
        this.checkOut = dateFormatter.format(searchParams.checkOut());
        this.nights = searchParams.nightsCount();
        this.adults = searchParams.adults();
        this.children = KidsSerializer.toString(searchParams.kids());
    }

    public long getHotelId() {
        return this.hotelId;
    }

    public int getLocationId() {
        return this.locationId;
    }

    public String getHotelName() {
        return this.hotelName;
    }

    public String getLocationName() {
        return this.locationName;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public int getPhotoCount() {
        return this.photoCount;
    }

    public String getHotelAddress() {
        return this.hotelAddress;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public boolean hasTrustYouData() {
        return this.hasTrustYouData;
    }

    public boolean hasFoursquareTips() {
        return this.hasFoursquareTips;
    }

    public int getRating() {
        return this.rating;
    }

    public int getStars() {
        return this.stars;
    }

    public String getCheckIn() {
        return this.checkIn;
    }

    public String getCheckOut() {
        return this.checkOut;
    }

    public int getAdults() {
        return this.adults;
    }

    public String getChildren() {
        return this.children;
    }

    public HotelData toHotelData() {
        HotelData hotelData = new HotelData();
        hotelData.setId(this.hotelId);
        hotelData.setName(this.hotelName);
        hotelData.setLocation(getLocation());
        hotelData.setAddress(this.hotelAddress);
        hotelData.setHasFoursquareTips(this.hasFoursquareTips);
        hotelData.setHasTrustYouData(this.hasTrustYouData);
        hotelData.setPhotoCount(this.photoCount);
        hotelData.setRating(this.rating);
        hotelData.setStars(this.stars);
        return hotelData;
    }

    public int getNights() {
        return this.nights;
    }

    public Coordinates getLocation() {
        Coordinates location = new Coordinates();
        location.setLat(this.latitude);
        location.setLon(this.longitude);
        return location;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.hotelId != ((FavoriteHotelDataEntity) o).hotelId) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (int) (this.hotelId ^ (this.hotelId >>> 32));
    }
}
