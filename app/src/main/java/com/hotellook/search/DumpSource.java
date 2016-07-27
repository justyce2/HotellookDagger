package com.hotellook.search;

import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

public class DumpSource {
    private Observable<LocationDumps> hotelsObservable;
    private final LocationDumpSource locationDumpSource;
    private Observable<Locations> locationsObservable;
    private final LocationsSource locationsSource;

    public DumpSource(LocationsSource locationsSource, LocationDumpSource locationDumpSource) {
        this.locationsSource = locationsSource;
        this.locationDumpSource = locationDumpSource;
    }

    public Observable<Boolean> observe(int locationId, String language) {
        return observe(observeAsList(locationId), language);
    }

    public Observable<Boolean> observe(Observable<List<Integer>> locationIdsObservable, String language) {
        this.locationsObservable = locationIdsObservable.flatMap(DumpSource$$Lambda$1.lambdaFactory$(this, language)).cache();
        this.hotelsObservable = Observable.combineLatest(this.locationsObservable.map(DumpSource$$Lambda$2.lambdaFactory$()), locationIdsObservable, DumpSource$$Lambda$3.lambdaFactory$()).flatMap(DumpSource$$Lambda$4.lambdaFactory$(this, language)).cache();
        return Observable.combineLatest(this.locationsObservable, this.hotelsObservable, DumpSource$$Lambda$5.lambdaFactory$());
    }

    /* synthetic */ Observable lambda$observe$0(String language, List locIds) {
        return this.locationsSource.observe(locIds, language);
    }

    /* synthetic */ Observable lambda$observe$1(String language, Pair pair) {
        return this.locationDumpSource.observe((List) pair.second, language, ((Integer) pair.first).intValue());
    }

    private Observable<List<Integer>> observeAsList(int locationId) {
        List<Integer> locationIds = new ArrayList();
        locationIds.add(Integer.valueOf(locationId));
        return Observable.just(locationIds);
    }

    public Observable<Locations> locationsObservable() {
        return this.locationsObservable;
    }

    public Observable<LocationDumps> locationDumpObservable() {
        return this.hotelsObservable;
    }
}
