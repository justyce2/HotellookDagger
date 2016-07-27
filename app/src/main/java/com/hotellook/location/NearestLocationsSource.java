package com.hotellook.location;

import android.location.Location;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.NetworkUtil;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class NearestLocationsSource {
    private static final String MAX_NEAREST_LOCATIONS = "3";
    private final HotellookService api;

    public NearestLocationsSource(HotellookService api) {
        this.api = api;
    }

    public Observable<List<GeoLocationData>> observe(Location location) {
        return createNearestLocationObservable(location, AndroidUtils.getLanguage()).doOnNext(NearestLocationsSource$$Lambda$1.lambdaFactory$(this)).doOnError(NearestLocationsSource$$Lambda$2.lambdaFactory$(this));
    }

    private void logNext(List<GeoLocationData> nearestLocations) {
        Timber.m755i("Nearest locations loaded: %s", nearestLocations);
    }

    private void logError(Throwable error) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading nearest locations: %s", error.getMessage());
            return;
        }
        Timber.m753e("Error while loading nearest locations: %s", error.getMessage());
    }

    private Observable<List<GeoLocationData>> createNearestLocationObservable(Location location, String language) {
        return this.api.locationByGeo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), MixPanelParams.BEST, MAX_NEAREST_LOCATIONS, language);
    }
}
