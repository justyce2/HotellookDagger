package com.hotellook.location;

import android.location.Location;
import android.support.annotation.Nullable;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.utils.Distances;
import com.hotellook.utils.LocationUtils;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class NearestLocationsProvider {
    private static final int MIN_DISTANCE_CHANGE_IN_METRES_TO_REACT = 1000;
    private Location lastKnownLocation;
    private List<GeoLocationData> nearestLocations;

    public NearestLocationsProvider(LastKnownLocationProvider lastKnownLocationProvider, NearestLocationsSource nearestLocationsSource) {
        Observable doOnNext = lastKnownLocationProvider.lastKnownLocationObservable().filter(NearestLocationsProvider$$Lambda$1.lambdaFactory$(this)).doOnNext(NearestLocationsProvider$$Lambda$2.lambdaFactory$(this)).doOnNext(NearestLocationsProvider$$Lambda$3.lambdaFactory$(this));
        nearestLocationsSource.getClass();
        doOnNext.flatMap(NearestLocationsProvider$$Lambda$4.lambdaFactory$(nearestLocationsSource)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(NearestLocationsProvider$$Lambda$5.lambdaFactory$(this), NearestLocationsProvider$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$new$0(Location location) {
        this.lastKnownLocation = location;
    }

    /* synthetic */ void lambda$new$1(List nearestLocations) {
        this.nearestLocations = nearestLocations;
    }

    private void logError(Throwable error) {
        Timber.m757w(error, "Unable to retrieve nearest locations on location update", new Object[0]);
    }

    private void logStartLoadingNearestLocations(Location location) {
        Timber.m755i("Started to load nearest locations for location: %s", location);
    }

    private boolean distanceChangedSufficiently(Location location) {
        return this.lastKnownLocation == null || LocationUtils.distanceBetween(location, this.lastKnownLocation) > Distances.KILOMETER;
    }

    @Nullable
    public List<GeoLocationData> nearestLocations() {
        return this.nearestLocations;
    }
}
