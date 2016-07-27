package com.hotellook.search;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.api.RequestFlags;
import com.hotellook.badges.Badges;
import com.hotellook.db.SearchDestinationFavoritesCache;
import com.hotellook.events.SearchCanceledEvent;
import com.hotellook.events.SearchFailEvent;
import com.hotellook.events.SearchFinishEvent;
import com.hotellook.filters.Filters;
import com.hotellook.ui.screen.searchresults.adapters.cards.controller.Cards;
import java.util.Set;
import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Search {
    private final Badges badges;
    private final Cards cards;
    private final Observable<Double> exchangeRateObservable;
    private final Filters filters;
    private boolean loading;
    private final Observable<LocationDumps> locationDumpObservable;
    private final Observable<Locations> locationsObservable;
    private final Observable<Offers> offersObservable;
    private final Observable<OffersSearchLaunchData> offersSearchLaunchObservable;
    private final RequestFlags requestFlags;
    private final Observable<Set<Integer>> respondedGatesObservable;
    private final Observable<RoomTypes> roomTypesObservable;
    private SearchData searchData;
    private final Observable<SearchData> searchObservable;
    private final SearchParams searchParams;
    private Subscription searchSubscription;
    private final long startTimestamp;

    public Search(Filters filters, Badges badges, Cards cards, Observable<Locations> locationsObservable, Observable<LocationDumps> locationDumpObservable, Observable<Offers> offersObservable, Observable<RoomTypes> roomTypesObservable, Observable<Double> exchangeRateObservable, Observable<SearchData> searchObservable, Observable<OffersSearchLaunchData> offersSearchLaunchObservable, Observable<Set<Integer>> respondedGatesObservable, SearchParams searchParams, RequestFlags requestFlags, SearchDestinationFavoritesCache favoritesCache, long startTimestamp) {
        this.loading = false;
        this.filters = filters;
        this.badges = badges;
        this.cards = cards;
        this.locationsObservable = locationsObservable;
        this.locationDumpObservable = locationDumpObservable;
        this.offersObservable = offersObservable;
        this.roomTypesObservable = roomTypesObservable;
        this.exchangeRateObservable = exchangeRateObservable;
        this.respondedGatesObservable = respondedGatesObservable;
        this.offersSearchLaunchObservable = offersSearchLaunchObservable;
        this.searchObservable = searchObservable;
        this.searchParams = searchParams;
        this.requestFlags = requestFlags;
        this.startTimestamp = startTimestamp;
        this.loading = true;
        this.searchSubscription = searchObservable.doOnNext(Search$$Lambda$1.lambdaFactory$(filters, badges, cards, favoritesCache)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Search$$Lambda$2.lambdaFactory$(this), Search$$Lambda$3.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$new$0(Filters filters, Badges badges, Cards cards, SearchDestinationFavoritesCache favoritesCache, SearchData searchData) {
        filters.init(searchData);
        badges.init(searchData);
        cards.init(searchData, filters);
        filters.filterInitData();
        favoritesCache.init(searchData.locations().all());
    }

    private void onSearchSuccess(SearchData searchData) {
        this.searchData = searchData;
        this.loading = false;
        HotellookApplication.eventBus().post(new SearchFinishEvent(searchData, this.badges));
    }

    private void onSearchFail(Throwable error) {
        this.loading = false;
        HotellookApplication.eventBus().post(new SearchFailEvent(this.searchParams, extractRetrofitError(error)));
    }

    private RetrofitError extractRetrofitError(Throwable error) {
        return error instanceof RetrofitError ? (RetrofitError) error : null;
    }

    public boolean isLoading() {
        return this.loading;
    }

    public Observable<Locations> destination() {
        return this.locationsObservable;
    }

    public Observable<Offers> prices() {
        return this.offersObservable;
    }

    public Observable<SearchData> observe() {
        return this.searchObservable;
    }

    public void cancel() {
        if (this.loading) {
            this.loading = false;
            this.searchSubscription.unsubscribe();
            HotellookApplication.eventBus().post(new SearchCanceledEvent(this.searchParams));
        }
    }

    public long startTimestamp() {
        return this.startTimestamp;
    }

    public SearchParams searchParams() {
        return this.searchParams;
    }

    @Nullable
    public SearchData searchData() {
        return this.searchData;
    }

    public Filters filters() {
        return this.filters;
    }

    public Badges badges() {
        return this.badges;
    }

    public Cards cards() {
        return this.cards;
    }

    public Observable<OffersSearchLaunchData> offersSearchLaunchObservable() {
        return this.offersSearchLaunchObservable;
    }

    public Observable<Set<Integer>> respondedGatesObservable() {
        return this.respondedGatesObservable;
    }

    public Observable<Locations> locationsObservable() {
        return this.locationsObservable;
    }
}
