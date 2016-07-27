package com.hotellook.search;

import android.location.Location;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.NetworkUtil;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class NearbyCitiesAvailabilitySource {
    private final HotellookService api;

    public NearbyCitiesAvailabilitySource(HotellookService api) {
        this.api = api;
    }

    public Observable<Boolean> observe(SearchParams searchParams) {
        if (searchParams.isDestinationTypeCity()) {
            return createNearestLocationObservable(searchParams.location()).map(NearbyCitiesAvailabilitySource$$Lambda$1.lambdaFactory$()).map(NearbyCitiesAvailabilitySource$$Lambda$2.lambdaFactory$()).doOnNext(NearbyCitiesAvailabilitySource$$Lambda$3.lambdaFactory$()).doOnError(NearbyCitiesAvailabilitySource$$Lambda$4.lambdaFactory$(this));
        }
        return Observable.just(Boolean.valueOf(false));
    }

    static /* synthetic */ Boolean lambda$observe$1(Integer locationsCount) {
        boolean z = true;
        if (locationsCount.intValue() <= 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    /* synthetic */ void lambda$observe$3(Throwable error) {
        logError(error);
    }

    private void logError(Throwable error) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading nearest locations: %s", error.getMessage());
            return;
        }
        Timber.m753e("Error while loading nearest locations: %s", error.getMessage());
    }

    private Observable<List<GeoLocationData>> createNearestLocationObservable(Location location) {
        return this.api.locationByGeo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), HotellookService.LOCATION_ANY, MixPanelParams.AUTO, AndroidUtils.getLanguage());
    }
}
