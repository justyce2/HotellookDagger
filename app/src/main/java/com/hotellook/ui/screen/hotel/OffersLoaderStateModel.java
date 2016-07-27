package com.hotellook.ui.screen.hotel;

import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.SearchParams;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.ui.screen.hotel.prices.OffersLoaderState;
import com.hotellook.utils.CollectionUtils;
import com.hotellook.utils.CompareUtils;
import java.util.Collections;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class OffersLoaderStateModel {
    private final HotelDataSource hotelDataSource;
    private final Source source;
    private OffersLoaderState state;
    private final Observable<OffersLoaderState> stateObservable;
    private final PublishSubject<OffersLoaderState> statePublishSubject;

    public OffersLoaderStateModel(HotelDataSource hotelDataSource, Source source) {
        this.statePublishSubject = PublishSubject.create();
        this.state = OffersLoaderState.UNKNOWN;
        this.source = source;
        this.hotelDataSource = hotelDataSource;
        this.stateObservable = this.statePublishSubject.doOnNext(OffersLoaderStateModel$$Lambda$1.lambdaFactory$(this)).replay(1).autoConnect(0);
        detectAndPublishInitialState();
        if (this.state == OffersLoaderState.UNKNOWN) {
            observeOffers(hotelDataSource);
        }
    }

    /* synthetic */ void lambda$new$0(OffersLoaderState offersLoaderState) {
        this.state = offersLoaderState;
    }

    private void observeOffers(HotelDataSource hotelDataSource) {
        Observable<List<Offer>> offersObservable = hotelDataSource.offersObservable();
        if (offersObservable != null) {
            this.statePublishSubject.onNext(OffersLoaderState.LOADING);
            offersObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(OffersLoaderStateModel$$Lambda$2.lambdaFactory$(this), OffersLoaderStateModel$$Lambda$3.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$observeOffers$1(List offers) {
        if (CollectionUtils.isNotEmpty(offers)) {
            this.statePublishSubject.onNext(OffersLoaderState.HAS_ROOMS);
        } else {
            this.statePublishSubject.onNext(OffersLoaderState.NO_ROOMS);
        }
    }

    /* synthetic */ void lambda$observeOffers$2(Throwable error) {
        this.statePublishSubject.onNext(OffersLoaderState.ERROR);
    }

    private void detectAndPublishInitialState() {
        this.statePublishSubject.onNext(detectState());
    }

    public Observable<OffersLoaderState> observeState() {
        return this.stateObservable;
    }

    public Offer getBestOffer() {
        return (Offer) Collections.min(this.hotelDataSource.offers(), CompareUtils.getComparatorByRoomPrice(this.hotelDataSource.discounts(), this.hotelDataSource.highlights()));
    }

    public String getRoomName() {
        return getBestOffer().getName();
    }

    public Source getSource() {
        return this.source;
    }

    public SearchParams getSearchParams() {
        return this.hotelDataSource.searchParams();
    }

    private OffersLoaderState detectState() {
        List<Offer> offers = this.hotelDataSource.offers();
        if (offers != null) {
            if (offers.size() > 0) {
                return OffersLoaderState.HAS_ROOMS;
            }
            return OffersLoaderState.NO_ROOMS;
        } else if (this.hotelDataSource.offersObservable() != null) {
            return OffersLoaderState.UNKNOWN;
        } else {
            if (this.source == Source.INTENT) {
                return OffersLoaderState.EMPTY;
            }
            if (this.source == Source.FAVORITES) {
                return OffersLoaderState.OUTDATED;
            }
            return OffersLoaderState.ERROR;
        }
    }

    public void onUpdateOutdated() {
        this.statePublishSubject.onNext(OffersLoaderState.EMPTY);
    }

    public void onChangeSearchParams() {
        this.statePublishSubject.onNext(OffersLoaderState.EMPTY);
    }

    public void onSearchStarted() {
        observeOffers(this.hotelDataSource);
        this.statePublishSubject.onNext(OffersLoaderState.LOADING);
    }

    public void onChangeParams() {
        this.statePublishSubject.onNext(OffersLoaderState.EMPTY);
    }

    public OffersLoaderState state() {
        return this.state;
    }

    public void onSearchFailed() {
        this.statePublishSubject.onNext(OffersLoaderState.ERROR);
    }

    @Nullable
    public DiscountsByRooms discounts() {
        return this.hotelDataSource.discounts();
    }

    @Nullable
    public HighlightsByRooms highlights() {
        return this.hotelDataSource.highlights();
    }
}
