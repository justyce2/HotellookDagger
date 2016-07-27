package com.hotellook.search;

import com.hotellook.api.RequestFlags;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.search.AsyncSearchResults;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.SearchId;
import com.hotellook.utils.NetworkUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.subjects.PublishSubject;
import timber.log.Timber;

public class AsyncOffersSource {
    private static final int RESULTS_PING_DELAY_SEC = 3;
    private final HotellookService api;
    private Observable<Offers> asyncSearchObservable;
    private Observable<AsyncSearchResults> asyncSearchResultsObservable;
    private PublishSubject<Boolean> asyncSearchResultsTrigger;
    private Discounts discounts;
    private Highlights highlights;
    private Map<Long, List<Offer>> hotels;
    private Observable<SearchId> launchAsyncSearchObservable;
    private Set<Integer> requestedGates;
    private Set<Integer> respondedGates;
    private final String token;

    private static class SearchLaunchData {
        private final List<Integer> locationIds;
        private final SearchId searchId;

        public SearchLaunchData(SearchId searchId, List<Integer> locationIds) {
            this.searchId = searchId;
            this.locationIds = locationIds;
        }
    }

    public AsyncOffersSource(HotellookService api, String token) {
        this.asyncSearchResultsTrigger = PublishSubject.create();
        this.hotels = new HashMap();
        this.requestedGates = new HashSet();
        this.respondedGates = new HashSet();
        this.api = api;
        this.token = token;
    }

    public Observable<Offers> observe(Observable<List<Integer>> locationIds, SearchParams searchParams, RequestFlags requestFlags) {
        this.launchAsyncSearchObservable = createAsyncSearchLaunchObservable(locationIds, searchParams, requestFlags);
        this.asyncSearchResultsObservable = createReadyOffersObservable(locationIds, searchParams, requestFlags);
        this.asyncSearchObservable = createOffersObservable();
        return this.asyncSearchObservable;
    }

    /* synthetic */ Observable lambda$observeRespondedGates$0(AsyncSearchResults asyncSearchResults) {
        return Observable.just(this.respondedGates);
    }

    public Observable<Set<Integer>> observeRespondedGates() {
        return this.asyncSearchResultsObservable.flatMap(AsyncOffersSource$$Lambda$1.lambdaFactory$(this));
    }

    public Observable<SearchId> observeOffersSearchLaunch() {
        return this.launchAsyncSearchObservable;
    }

    /* synthetic */ Observable lambda$observeDiscounts$1(Offers asyncSearchObservable) {
        return Observable.just(this.discounts);
    }

    public Observable<Discounts> observeDiscounts() {
        return this.asyncSearchObservable.flatMap(AsyncOffersSource$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ Observable lambda$observeHighlights$2(Offers asyncSearchObservable) {
        return Observable.just(this.highlights);
    }

    public Observable<Highlights> observeHighlights() {
        return this.asyncSearchObservable.flatMap(AsyncOffersSource$$Lambda$3.lambdaFactory$(this));
    }

    private Observable<Offers> createOffersObservable() {
        return this.asyncSearchResultsObservable.toList().flatMap(AsyncOffersSource$$Lambda$4.lambdaFactory$(this)).doOnNext(AsyncOffersSource$$Lambda$5.lambdaFactory$(this)).replay().autoConnect(0);
    }

    /* synthetic */ Observable lambda$createOffersObservable$3(List resultsList) {
        return Observable.just(new Offers(this.hotels));
    }

    /* synthetic */ void lambda$createOffersObservable$4(Offers offers) {
        logSuccess(offers);
    }

    private Observable<AsyncSearchResults> createReadyOffersObservable(Observable<List<Integer>> locationIds, SearchParams searchParams, RequestFlags requestFlags) {
        return Observable.combineLatest(this.launchAsyncSearchObservable, this.asyncSearchResultsTrigger, locationIds, AsyncOffersSource$$Lambda$6.lambdaFactory$()).delay(3, TimeUnit.SECONDS).flatMap(AsyncOffersSource$$Lambda$7.lambdaFactory$(this, searchParams, requestFlags)).doOnNext(AsyncOffersSource$$Lambda$8.lambdaFactory$(this)).doOnNext(AsyncOffersSource$$Lambda$9.lambdaFactory$(this)).doOnError(AsyncOffersSource$$Lambda$10.lambdaFactory$(this, searchParams)).takeUntil(AsyncOffersSource$$Lambda$11.lambdaFactory$(this)).replay().autoConnect(0);
    }

    /* synthetic */ Observable lambda$createReadyOffersObservable$6(SearchParams searchParams, RequestFlags requestFlags, SearchLaunchData searchLaunchData) {
        return this.api.readyOffers(OffersSearchParamsCreator.create(searchParams, requestFlags, searchLaunchData.locationIds, this.token, this.respondedGates));
    }

    /* synthetic */ void lambda$createReadyOffersObservable$7(AsyncSearchResults asyncSearchResults) {
        this.hotels.putAll(asyncSearchResults.hotels());
        this.respondedGates.addAll(asyncSearchResults.gates());
        if (allGatesReceived(asyncSearchResults)) {
            onLastResult(asyncSearchResults);
        } else {
            this.asyncSearchResultsTrigger.onNext(Boolean.valueOf(true));
        }
    }

    /* synthetic */ void lambda$createReadyOffersObservable$8(AsyncSearchResults asyncSearchResults) {
        Timber.m751d("Responded gates: %s", this.respondedGates);
    }

    /* synthetic */ void lambda$createReadyOffersObservable$9(SearchParams searchParams, Throwable error) {
        logError(error, searchParams);
    }

    private void onLastResult(AsyncSearchResults asyncSearchResults) {
        this.discounts = new Discounts(asyncSearchResults.discountsByRoom());
        this.highlights = new Highlights(asyncSearchResults.highlights());
    }

    private Observable<SearchId> createAsyncSearchLaunchObservable(Observable<List<Integer>> locationIds, SearchParams params, RequestFlags requestFlags) {
        return locationIds.flatMap(AsyncOffersSource$$Lambda$12.lambdaFactory$(this, params, requestFlags)).doOnNext(AsyncOffersSource$$Lambda$13.lambdaFactory$()).doOnNext(AsyncOffersSource$$Lambda$14.lambdaFactory$(this)).doOnNext(AsyncOffersSource$$Lambda$15.lambdaFactory$(this)).replay().autoConnect(0);
    }

    /* synthetic */ Observable lambda$createAsyncSearchLaunchObservable$10(SearchParams params, RequestFlags requestFlags, List locations) {
        return this.api.launchAsyncSearch(OffersSearchParamsCreator.create(params, requestFlags, locations, this.token));
    }

    /* synthetic */ void lambda$createAsyncSearchLaunchObservable$12(SearchId searchId) {
        this.requestedGates.addAll(searchId.gates());
    }

    /* synthetic */ void lambda$createAsyncSearchLaunchObservable$13(SearchId searchId) {
        this.asyncSearchResultsTrigger.onNext(Boolean.valueOf(true));
    }

    private boolean allGatesReceived(AsyncSearchResults asyncSearchResults) {
        return asyncSearchResults.isStop();
    }

    private void logError(Throwable error, SearchParams searchParams) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m757w(error, "Connection error while loading offers: %s", error.getMessage());
            return;
        }
        Timber.m754e(error, "Error while loading offers for %s: %s", searchParams, error.getMessage());
    }

    private void logSuccess(Offers offers) {
        Timber.m755i("Loaded offers count: %s", Integer.valueOf(offers.all().size()));
    }
}
