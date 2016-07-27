package com.hotellook.core.api;

import com.hotellook.core.api.pojo.autocomlete.AutocompleteData;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.deeplink.DeeplinkData;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.hotelsdump.HotelsDump;
import com.hotellook.core.api.pojo.minprice.ColoredMinPriceCalendar;
import com.hotellook.core.api.pojo.search.AsyncSearchResults;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.SearchId;
import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import rx.Observable;

public interface HotellookService {
    public static final int ALLOW_EN = 1;
    public static final int DISALLOW_EN = 0;
    public static final String HOST_PHONE = "android.phone.hotellook";
    public static final String HOST_TABLET = "android.tablet.hotellook";
    public static final String LOCATION_ANY = "any";
    public static final String LOCATION_BEST = "best";
    public static final String LOCATION_LIMIT_AUTO = "auto";

    @GET("/autocomplete")
    void autocomplete(@Query("term") String str, @Query("lang") String str2, Callback<AutocompleteData> callback);

    @GET("/static/city_info_mobile/{language}/{locationId}.json")
    Observable<CityInfo> cityInfo(@Path("language") String str, @Path("locationId") int i);

    @GET("/static/city_info_mobile/{language}/{locationId}.json")
    void cityInfo(@Path("language") String str, @Path("locationId") int i, Callback<CityInfo> callback);

    @GET("/adaptors/mobile_deeplink.json")
    Observable<DeeplinkData> deeplink(@QueryMap Map<String, Object> map);

    @GET("/adaptors/mobile_deeplink.json")
    void deeplink(@Query("locationId") int i, @Query("checkIn") String str, @Query("checkOut") String str2, @Query("adults") int i2, @Query("children") String str3, @Query("mobileToken") String str4, @Query("marker") String str5, @Query("currency") String str6, @Query("language") String str7, @Query("hotelId") long j, @Query("gateId") int i3, @Query("roomId") int i4, Callback<DeeplinkData> callback);

    @GET("/static/city_info_mobile/{language}/{locationId}.json")
    CityInfo getCityInfo(@Path("language") String str, @Path("locationId") int i);

    @GET("/static/hotel_mobile/{language}/{hotelId}.json")
    HotelDetailData getHotelDetail(@Path("language") String str, @Path("hotelId") long j, @Query("real_rating") int i);

    @GET("/static/hotel_mobile/{language}/{hotelId}.json")
    Observable<HotelDetailData> hotelDetail(@Path("language") String str, @Path("hotelId") long j, @Query("real_rating") int i);

    @GET("/static/hotel_mobile/{language}/{hotelId}.json")
    void hotelDetail(@Path("language") String str, @Path("hotelId") long j, @Query("real_rating") int i, Callback<HotelDetailData> callback);

    @GET("/static/city_extended_mobile/{language}/{locationId}.json")
    Observable<HotelsDump> hotelsDump(@Path("language") String str, @Path("locationId") int i, @Query("priceless") int i2, @Query("real_rating") int i3);

    @GET("/static/city_extended_mobile/{language}/{locationId}.json")
    void hotelsDump(@Path("language") String str, @Path("locationId") int i, @Query("priceless") int i2, @Query("real_rating") int i3, Callback<HotelsDump> callback);

    @GET("/adaptors/mobile_search.json")
    Observable<SearchId> launchAsyncSearch(@QueryMap Map<String, Object> map);

    @GET("/location")
    Observable<List<GeoLocationData>> locationByGeo(@Query("latitude") String str, @Query("longitude") String str2, @Query("match") String str3, @Query("limit") String str4, @Query("lang") String str5);

    @GET("/location")
    void locationByGeo(@Query("latitude") String str, @Query("longitude") String str2, @Query("match") String str3, @Query("limit") String str4, @Query("lang") String str5, Callback<List<GeoLocationData>> callback);

    @GET("/static/city_multiple_mobile/{language}/{locationIds}.json")
    Observable<Map<Integer, HotelsDump>> multiLocationHotelsDump(@Path("language") String str, @Path("locationIds") String str2, @Query("priceless") int i, @Query("real_rating") int i2);

    @GET("/static/city_info_multiple_mobile/{language}/{locationIds}.json")
    Observable<Map<Integer, CityInfo>> multipleCityInfo(@Path("language") String str, @Path("locationIds") String str2);

    @GET("/minprices/location_calendar/{locationId}.json")
    Observable<ColoredMinPriceCalendar> observeCalendarMinPrices(@Path("locationId") int i, @Query("currency") String str, @Query("adults") int i2, @Query("simple") int i3);

    @GET("/adaptors/currency.json")
    Observable<Double> observeExchangeRate(@Query("from") String str, @Query("to") String str2);

    @GET("/dictionary/{language}/room_types.json")
    Observable<Map<Integer, String>> observeRoomTypes(@Path("language") String str);

    @Deprecated
    @GET("/adaptors/mobile_search_s.json")
    Observable<Map<Long, List<Offer>>> offers(@Query("locationId") int i, @Query("hotelId") long j, @Query("checkIn") String str, @Query("checkOut") String str2, @Query("adults") int i2, @Query("children") String str3, @Query("mobileToken") String str4, @Query("marker") String str5, @Query("currency") String str6, @Query("language") String str7, @Query("host") String str8, @Query("discounts") int i3, @Query("flags[mobile_source]") String str9, @Query("flags[mobile_section]") String str10, @Query("flags[mobile_func]") String str11, @Query("flags[mobile_badge]") String str12, @Query("flags[utm]") String str13, @Query("flags[utmDetail]") String str14, @Query("allowEn") int i4, @Query("uuid") String str15);

    @Deprecated
    @GET("/adaptors/mobile_search_s.json")
    Observable<Map<Long, List<Offer>>> offers(@Query("locationId") int i, @Query("checkIn") String str, @Query("checkOut") String str2, @Query("adults") int i2, @Query("children") String str3, @Query("mobileToken") String str4, @Query("marker") String str5, @Query("currency") String str6, @Query("language") String str7, @Query("host") String str8, @Query("discounts") int i3, @Query("flags[mobile_source]") String str9, @Query("flags[mobile_section]") String str10, @Query("flags[mobile_func]") String str11, @Query("flags[mobile_badge]") String str12, @Query("flags[utm]") String str13, @Query("flags[utmDetail]") String str14, @Query("allowEn") int i4, @Query("uuid") String str15);

    @GET("/adaptors/mobile_search_s.json")
    Observable<Map<Integer, Map<Long, List<Offer>>>> offersInLocations(@QueryMap Map<String, Object> map);

    @GET("/adaptors/mobile_results.json")
    Observable<AsyncSearchResults> readyOffers(@QueryMap Map<String, Object> map);
}
