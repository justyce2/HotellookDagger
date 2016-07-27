package com.hotellook.search;

import android.content.Context;
import android.support.annotation.NonNull;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.api.RequestFlagsGenerator;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.db.FavoritesRepository;
import com.hotellook.db.data.FavoriteHotelDataEntity;
import com.hotellook.dependencies.HotellookComponent;
import com.hotellook.events.SearchStartEvent;
import com.hotellook.utils.EventBus;
import java.util.List;
import java.util.Map;
import java.util.Set;
import rx.Observable;
import timber.log.Timber;

public class SearchEngine {
    private final HotellookComponent component;
    private final DumpCacher dumpCacher;
    private final EventBus eventBus;
    private final FavoritesRepository favoritesRepository;

    public SearchEngine(@NonNull Context context, @NonNull DumpCacher dumpCacher, @NonNull EventBus eventBus, FavoritesRepository favoritesRepository) {
        this.component = HotellookApplication.from(context).getComponent();
        this.dumpCacher = dumpCacher;
        this.eventBus = eventBus;
        this.favoritesRepository = favoritesRepository;
    }

    public void makeSearch(@NonNull SearchParams searchParams, @NonNull SearchStartEvent event) {
        Observable<Locations> locationsObservable;
        Observable<LocationDumps> locationDumpsObservable;
        Timber.m755i("Search. New search: %s", searchParams.toString());
        this.eventBus.post(event);
        RequestFlags requestFlags = new RequestFlagsGenerator().citySearchFromSearchForm(searchParams);
        long startTimestamp = System.currentTimeMillis();
        String language = searchParams.language();
        Observable<List<Integer>> locationIdsObservable = this.component.locationIdsSource().observe(searchParams).cache();
        if (searchParams.isDestinationTypeCity() && hasCache(searchParams.locationId(), language)) {
            Timber.m755i("Search. Using pre cashed dump observables", new Object[0]);
            DumpCache dumpCache = this.dumpCacher.obtainCache(searchParams.locationId(), language);
            locationsObservable = dumpCache.locationsObservable();
            locationDumpsObservable = dumpCache.locationDumpObservable();
        } else {
            DumpSource dumpSource = this.component.dumpSource();
            dumpSource.observe((Observable) locationIdsObservable, language);
            locationsObservable = dumpSource.locationsObservable();
            locationDumpsObservable = dumpSource.locationDumpObservable();
        }
        AsyncOffersSource asyncOffersSource = this.component.asyncOffersSource();
        Observable<Offers> offersObservable = observeOffers(locationIdsObservable, searchParams, requestFlags, asyncOffersSource);
        Observable<OffersSearchLaunchData> offersSearchLaunchObservable = observeOffersLaunch(asyncOffersSource);
        Observable<Set<Integer>> respondedGatesObservable = asyncOffersSource.observeRespondedGates();
        Observable<Highlights> highlightsObservable = asyncOffersSource.observeHighlights();
        Observable<Discounts> discountsObservable = asyncOffersSource.observeDiscounts();
        Observable<RoomTypes> roomTypesObservable = observeRoomTypes(language);
        Observable<Double> exchangeRateObservable = observeExchangeRate(searchParams.currency());
        Observable<Locations> observable = locationsObservable;
        Observable<LocationDumps> observable2 = locationDumpsObservable;
        Observable<Offers> observable3 = offersObservable;
        Observable<RoomTypes> observable4 = roomTypesObservable;
        Observable<Double> observable5 = exchangeRateObservable;
        this.component.searchKeeper().add(new Search(this.component.filters(), this.component.badges(), this.component.cards(), observable, observable2, observable3, observable4, observable5, locationsObservable.flatMap(SearchEngine$$Lambda$1.lambdaFactory$(this, locationsObservable, locationDumpsObservable, offersObservable, roomTypesObservable, exchangeRateObservable, searchParams, requestFlags, observeNearbyCitiesAvailability(searchParams), discountsObservable, highlightsObservable)).doOnNext(SearchEngine$$Lambda$2.lambdaFactory$(this)).cache(), offersSearchLaunchObservable, respondedGatesObservable, searchParams, requestFlags, this.component.getLocationFavoritesCache(), startTimestamp));
    }

    /* synthetic */ Observable lambda$makeSearch$0(Observable locationsObservable, Observable locationDumpsObservable, Observable offersObservable, Observable roomTypesObservable, Observable exchangeRateObservable, @NonNull SearchParams searchParams, RequestFlags requestFlags, Observable nearbySearchAvailabilityObservable, Observable discountsObservable, Observable highlightsObservable, Locations locations) {
        return combineToSearchObservable(locationsObservable, locationDumpsObservable, offersObservable, roomTypesObservable, exchangeRateObservable, searchParams, requestFlags, nearbySearchAvailabilityObservable, discountsObservable, highlightsObservable);
    }

    /* synthetic */ void lambda$makeSearch$1(SearchData searchData) {
        updateOffersForFavorites(searchData);
    }

    private void updateOffersForFavorites(SearchData searchData) {
        SearchParams searchParams = searchData.searchParams();
        Map<Long, List<Offer>> offers = searchData.offers().all();
        for (CityInfo cityInfo : searchData.locations().all()) {
            for (FavoriteHotelDataEntity favEntity : this.favoritesRepository.getAllInLocation(cityInfo.getId())) {
                long hotelId = favEntity.getHotelId();
                if (offers.containsKey(Long.valueOf(hotelId))) {
                    this.favoritesRepository.update(searchData.hotels().findById(hotelId), cityInfo, searchParams);
                }
            }
        }
    }

    private Observable<Boolean> observeNearbyCitiesAvailability(@NonNull SearchParams searchParams) {
        return this.component.nearbySearchAvailabilitySource().observe(searchParams).replay().autoConnect(0);
    }

    private Observable<OffersSearchLaunchData> observeOffersLaunch(AsyncOffersSource offersSource) {
        return offersSource.observeOffersSearchLaunch().map(SearchEngine$$Lambda$3.lambdaFactory$());
    }

    @NonNull
    private Observable<SearchData> combineToSearchObservable(Observable<Locations> locationsObservable, Observable<LocationDumps> locationDumpObservable, Observable<Offers> pricesObservable, Observable<RoomTypes> roomTypesObservable, Observable<Double> exchangeRateObservable, SearchParams searchParams, RequestFlags requestFlags, Observable<Boolean> nearbyCitiesAvailabilityObservable, Observable<Discounts> discountsObservable, Observable<Highlights> highlightsObservable) {
        return Observable.combineLatest(locationsObservable, locationDumpObservable, pricesObservable, roomTypesObservable, exchangeRateObservable, nearbyCitiesAvailabilityObservable, discountsObservable, highlightsObservable, SearchEngine$$Lambda$4.lambdaFactory$(searchParams, requestFlags));
    }

    private boolean hasCache(int locationId, String language) {
        return this.dumpCacher.obtainCache(locationId, language) != null;
    }

    private Observable<Double> observeExchangeRate(String currency) {
        return this.component.exchangeRateSource().observeExchange(currency).doOnNext(SearchEngine$$Lambda$5.lambdaFactory$(this)).cache();
    }

    /* synthetic */ void lambda$observeExchangeRate$4(Double exchangeRate) {
        this.component.getCurrencyConverter().setRate(exchangeRate.doubleValue());
    }

    private Observable<RoomTypes> observeRoomTypes(String language) {
        return this.component.roomTypesSource().observe(language).cache();
    }

    private Observable<Offers> observeOffers(Observable<List<Integer>> locationIdsObservable, SearchParams searchParams, RequestFlags requestFlags, AsyncOffersSource offersSource) {
        return offersSource.observe(locationIdsObservable, searchParams, requestFlags).cache();
    }
}
