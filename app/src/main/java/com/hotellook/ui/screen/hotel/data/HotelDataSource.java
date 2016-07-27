package com.hotellook.ui.screen.hotel.data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.api.dataloaders.StreetViewCheckLoader;
import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.HotelDataLoadedEvent;
import com.hotellook.events.HotelSearchFinishEvent;
import com.hotellook.search.AsyncOffersSource;
import com.hotellook.search.Discounts;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.Highlights;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.Offers;
import com.hotellook.search.RoomTypes;
import com.hotellook.search.Search;
import com.hotellook.search.SearchData;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.searchform.LaunchParams;
import com.hotellook.ui.view.appbar.ToolbarScrollingBehavior;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.EventBus;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HotelDataSource {
    private Observable<Boolean> allDataObservable;
    private BasicHotelData basicHotelData;
    private Observable<BasicHotelData> basicHotelDataObservable;
    private CityInfo cityInfo;
    private final HotellookComponent component;
    private DiscountsByRooms discounts;
    private final EventBus eventBus;
    private HighlightsByRooms highlights;
    private HotelDetailData hotelDetailData;
    private Observable<HotelDetailData> hotelDetailObservable;
    private long hotelId;
    private List<Offer> hotelOffers;
    private LaunchParams launchParams;
    private Observable<CityInfo> locationObservable;
    private Observable<List<Offer>> offersObservable;
    private long offersTimestamp;
    @Nullable
    private RequestFlags requestFlags;
    private RoomTypes roomTypes;
    private Observable<RoomTypes> roomTypesObservable;
    private Observable<SearchInfoForBooking> searchInfoForBookingObservable;
    @Nullable
    private SearchParams searchParams;
    private StreetViewCheckLoader streetViewCheckLoader;

    public HotelDataSource(Context context, EventBus eventBus) {
        this.component = HotellookApplication.from(context).getComponent();
        this.eventBus = eventBus;
    }

    public void observeFromSearchForm(SearchParams searchParams, RequestFlags flags) {
        this.searchParams = searchParams;
        this.requestFlags = flags;
        this.hotelId = searchParams.hotelId();
        int locationId = searchParams.locationId();
        String language = searchParams.language();
        this.hotelDetailObservable = observeHotelDetail(language, Long.valueOf(this.hotelId));
        this.basicHotelDataObservable = createBasicObservable(this.hotelDetailObservable);
        this.locationObservable = createLocationObservable(locationId, language);
        this.roomTypesObservable = createRoomTypesObservable(language);
        this.offersObservable = createOffersObservable(getLocationIdInList(locationId), this.roomTypesObservable);
        createFullDataObservable();
        subscribeToFullData();
    }

    @NonNull
    private Observable<SearchInfoForBooking> searchInfoForBookingObservable(List<Integer> locationIds, AsyncOffersSource asyncOffersSource) {
        return asyncOffersSource.observeOffersSearchLaunch().map(HotelDataSource$$Lambda$1.lambdaFactory$(locationIds));
    }

    public void observeFromIntent(@Nullable LaunchParams launchParams) {
        if (launchParams != null) {
            this.hotelId = launchParams.getHotelId().longValue();
            this.launchParams = launchParams;
        }
        String language = AndroidUtils.getLanguage();
        this.hotelDetailObservable = observeHotelDetail(language, Long.valueOf(this.hotelId));
        Observable<Integer> locationIdObservable = this.hotelDetailObservable.map(HotelDataSource$$Lambda$2.lambdaFactory$());
        this.basicHotelDataObservable = createBasicObservable(this.hotelDetailObservable);
        this.locationObservable = locationIdObservable.flatMap(HotelDataSource$$Lambda$3.lambdaFactory$(this, language)).cache();
        createFullDataObservable();
        subscribeToFullData();
    }

    /* synthetic */ Observable lambda$observeFromIntent$1(String language, Integer locationId) {
        return createLocationObservable(locationId.intValue(), language);
    }

    public void observeFromSearch(long hotelId, Search search, List<Offer> offers, long timestamp) {
        SearchData searchData = search.searchData();
        this.offersTimestamp = timestamp;
        this.searchParams = searchData.searchParams();
        this.requestFlags = searchData.requestFlags();
        this.discounts = searchData.discounts().get(hotelId);
        this.highlights = searchData.highlights().get(hotelId);
        this.hotelId = hotelId;
        int locationId = searchData.hotels().findLocationId(hotelId);
        this.roomTypes = searchData.roomTypes();
        this.basicHotelData = new BasicHotelData(searchData.hotels().findById(hotelId));
        this.cityInfo = searchData.locations().getById(locationId);
        this.hotelOffers = offers;
        this.offersObservable = Observable.just(offers);
        this.roomTypesObservable = Observable.just(this.roomTypes);
        this.locationObservable = Observable.just(this.cityInfo);
        this.basicHotelDataObservable = Observable.just(this.basicHotelData);
        this.hotelDetailObservable = observeHotelDetail(this.searchParams.language(), Long.valueOf(hotelId));
        this.searchInfoForBookingObservable = Observable.combineLatest(search.offersSearchLaunchObservable(), search.locationsObservable(), HotelDataSource$$Lambda$4.lambdaFactory$());
        createFullDataObservable();
        subscribeToFullData();
    }

    public void observeFromFavorites(FavoriteHotelDataEntity favoriteHotelData, List<Offer> offers, RoomTypes roomTypes, long offersTimestamp, SearchParams searchParams) {
        this.searchParams = searchParams;
        this.requestFlags = new RequestFlagsGenerator().citySearchFromSearchForm(searchParams);
        this.hotelId = favoriteHotelData.getHotelId();
        String language = searchParams.language();
        int locationId = favoriteHotelData.getLocationId();
        if (offers != null) {
            this.hotelOffers = offers;
            this.offersTimestamp = offersTimestamp;
            this.offersObservable = Observable.just(this.hotelOffers);
            this.roomTypes = roomTypes;
            this.roomTypesObservable = Observable.just(roomTypes);
        }
        this.basicHotelData = new BasicHotelData(favoriteHotelData);
        this.basicHotelDataObservable = Observable.just(this.basicHotelData);
        this.hotelDetailObservable = observeHotelDetail(language, Long.valueOf(this.hotelId));
        this.locationObservable = createLocationObservable(locationId, language);
        createFullDataObservable();
        subscribeToFullData();
    }

    public void observeFromFavorites(FavoriteHotelDataEntity favoriteHotelData, SearchParams searchParams) {
        observeFromFavorites(favoriteHotelData, null, null, 0, searchParams);
    }

    private Observable<BasicHotelData> createBasicObservable(Observable<HotelDetailData> hotelDetailObservable) {
        return hotelDetailObservable.map(HotelDataSource$$Lambda$5.lambdaFactory$()).doOnNext(HotelDataSource$$Lambda$6.lambdaFactory$(this)).cache();
    }

    /* synthetic */ void lambda$createBasicObservable$3(BasicHotelData basicHotelData) {
        this.basicHotelData = basicHotelData;
    }

    private void subscribeToFullData() {
        this.allDataObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(HotelDataSource$$Lambda$7.lambdaFactory$(), HotelDataSource$$Lambda$8.lambdaFactory$());
    }

    private void createFullDataObservable() {
        this.allDataObservable = Observable.combineLatest(this.hotelDetailObservable, this.locationObservable, HotelDataSource$$Lambda$9.lambdaFactory$()).doOnNext(HotelDataSource$$Lambda$10.lambdaFactory$(this)).map(HotelDataSource$$Lambda$11.lambdaFactory$()).cache();
    }

    /* synthetic */ void lambda$createFullDataObservable$7(Pair pair) {
        this.eventBus.post(new HotelDataLoadedEvent((HotelDetailData) pair.first, (CityInfo) pair.second));
    }

    private Observable<CityInfo> createLocationObservable(int locationId, String language) {
        return this.component.locationSource().observe(getLocationIdInList(locationId), language).map(HotelDataSource$$Lambda$12.lambdaFactory$(locationId)).doOnNext(HotelDataSource$$Lambda$13.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$createLocationObservable$10(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
    }

    @NonNull
    private List<Integer> getLocationIdInList(int locationId) {
        List<Integer> locationIds = new ArrayList(1);
        locationIds.add(Integer.valueOf(locationId));
        return locationIds;
    }

    private Observable<RoomTypes> createRoomTypesObservable(String language) {
        return this.component.roomTypesSource().observe(language).doOnNext(HotelDataSource$$Lambda$14.lambdaFactory$(this)).cache();
    }

    /* synthetic */ void lambda$createRoomTypesObservable$11(RoomTypes roomTypes) {
        this.roomTypes = roomTypes;
    }

    @NonNull
    private Observable<List<Offer>> createOffersObservable(List<Integer> locationIds, Observable<RoomTypes> roomTypesObservable) {
        AsyncOffersSource asyncOffersSource = this.component.asyncOffersSource();
        Observable<List<Offer>> offersObservable = asyncOffersSource.observe(Observable.just(locationIds), this.searchParams, this.requestFlags).map(HotelDataSource$$Lambda$15.lambdaFactory$(this)).doOnNext(HotelDataSource$$Lambda$16.lambdaFactory$(this)).doOnSubscribe(HotelDataSource$$Lambda$17.lambdaFactory$(this)).doOnNext(HotelDataSource$$Lambda$18.lambdaFactory$(this)).cache();
        this.searchInfoForBookingObservable = searchInfoForBookingObservable(locationIds, asyncOffersSource);
        return Observable.combineLatest(roomTypesObservable, offersObservable, asyncOffersSource.observeDiscounts().map(HotelDataSource$$Lambda$19.lambdaFactory$(this)).doOnNext(HotelDataSource$$Lambda$20.lambdaFactory$(this)), asyncOffersSource.observeHighlights().map(HotelDataSource$$Lambda$21.lambdaFactory$(this)).doOnNext(HotelDataSource$$Lambda$22.lambdaFactory$(this)), HotelDataSource$$Lambda$23.lambdaFactory$());
    }

    /* synthetic */ List lambda$createOffersObservable$12(Offers offers) {
        return (List) offers.all().get(Long.valueOf(this.hotelId));
    }

    /* synthetic */ void lambda$createOffersObservable$13(List offers) {
        this.hotelOffers = offers;
    }

    /* synthetic */ void lambda$createOffersObservable$14() {
        this.offersTimestamp = setSearchStartTime();
    }

    /* synthetic */ void lambda$createOffersObservable$15(List offers) {
        this.eventBus.post(new HotelSearchFinishEvent(this.searchParams, offers));
    }

    /* synthetic */ DiscountsByRooms lambda$createOffersObservable$16(Discounts allHotelsDiscounts) {
        return allHotelsDiscounts.get(this.hotelId);
    }

    /* synthetic */ void lambda$createOffersObservable$17(DiscountsByRooms discountsInHotel) {
        this.discounts = discountsInHotel;
    }

    /* synthetic */ HighlightsByRooms lambda$createOffersObservable$18(Highlights allHotelsHighlights) {
        return allHotelsHighlights.get(this.hotelId);
    }

    /* synthetic */ void lambda$createOffersObservable$19(HighlightsByRooms highlightsInHotel) {
        this.highlights = highlightsInHotel;
    }

    static /* synthetic */ List lambda$createOffersObservable$20(RoomTypes roomTypes, List offers, DiscountsByRooms discounts, HighlightsByRooms highlights) {
        return offers;
    }

    @NonNull
    private Observable<HotelDetailData> observeHotelDetail(String language, Long hotelId) {
        return this.component.hotelDetailSource().observe(language, hotelId.longValue()).doOnNext(HotelDataSource$$Lambda$24.lambdaFactory$(this)).doOnNext(HotelDataSource$$Lambda$25.lambdaFactory$(this)).cache();
    }

    /* synthetic */ void lambda$observeHotelDetail$21(HotelDetailData hotelDetailData) {
        this.hotelDetailData = hotelDetailData;
    }

    /* synthetic */ void lambda$observeHotelDetail$22(HotelDetailData hotelDetailData) {
        initStreetViewLoader(hotelDetailData.getLocation());
    }

    private long setSearchStartTime() {
        long currentTimeMillis = System.currentTimeMillis();
        this.offersTimestamp = currentTimeMillis;
        return currentTimeMillis;
    }

    public Observable<BasicHotelData> basicHotelDataObservable() {
        return this.basicHotelDataObservable;
    }

    public List<Offer> offers() {
        return this.hotelOffers;
    }

    @Nullable
    public SearchParams searchParams() {
        return this.searchParams;
    }

    public Observable<CityInfo> locationObservable() {
        return this.locationObservable;
    }

    public BasicHotelData basicHotelData() {
        return this.basicHotelData;
    }

    public CityInfo cityInfo() {
        return this.cityInfo;
    }

    private boolean initStreetViewLoader(Coordinates hotelLocation) {
        if (this.streetViewCheckLoader == null) {
            this.streetViewCheckLoader = this.component.getStreetViewLoader();
            this.streetViewCheckLoader.load(hotelLocation, ToolbarScrollingBehavior.ANIMATION_DURATION);
        }
        return true;
    }

    public boolean hasStreetView() {
        return this.streetViewCheckLoader != null && this.streetViewCheckLoader.hasStreetView();
    }

    public RoomTypes roomTypes() {
        return this.roomTypes;
    }

    public long searchStartTimestamp() {
        return this.offersTimestamp;
    }

    public HotelDetailData detailHotelData() {
        return this.hotelDetailData;
    }

    @Nullable
    public RequestFlags requestFlags() {
        return this.requestFlags;
    }

    public void updateOffers(SearchParams searchParams, RequestFlags requestFlags) {
        this.searchParams = searchParams;
        this.requestFlags = requestFlags;
        this.offersObservable = createOffersObservable(getLocationIdInList(this.cityInfo.getId()), createRoomTypesObservable(searchParams.language()));
    }

    public Observable<Boolean> allDataObservable() {
        return this.allDataObservable;
    }

    public boolean hasBasicData() {
        return (this.cityInfo == null || this.basicHotelData == null) ? false : true;
    }

    public Observable<HotelDetailData> hotelDetailObservable() {
        return this.hotelDetailObservable;
    }

    public boolean hasAllHotelData() {
        return hasBasicData() && this.hotelDetailData != null;
    }

    public Observable<List<Offer>> offersObservable() {
        return this.offersObservable;
    }

    public void updateBasicData() {
        if (this.searchParams != null) {
            observeFromSearchForm(this.searchParams, this.requestFlags);
        } else {
            observeFromIntent(null);
        }
    }

    public LaunchParams launchParams() {
        return this.launchParams;
    }

    public Observable<SearchInfoForBooking> searchInfoForBooking() {
        return this.searchInfoForBookingObservable;
    }

    public DiscountsByRooms discounts() {
        return this.discounts;
    }

    public HighlightsByRooms highlights() {
        return this.highlights;
    }
}
