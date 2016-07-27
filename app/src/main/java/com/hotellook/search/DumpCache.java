package com.hotellook.search;

import android.support.annotation.NonNull;
import rx.Observable;

public class DumpCache {
    @NonNull
    private final Observable<LocationDumps> hotelsObservable;
    @NonNull
    private final Observable<Locations> locationsObservable;

    public DumpCache(@NonNull Observable<Locations> locationsObservable, @NonNull Observable<LocationDumps> hotelsObservable) {
        this.locationsObservable = locationsObservable;
        this.hotelsObservable = hotelsObservable;
    }

    @NonNull
    public Observable<Locations> locationsObservable() {
        return this.locationsObservable;
    }

    @NonNull
    public Observable<LocationDumps> locationDumpObservable() {
        return this.hotelsObservable;
    }
}
