package com.hotellook.search;

import android.location.Location;
import android.support.annotation.NonNull;
import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.geo.GeoLocationData;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.AndroidUtils;
import com.hotellook.utils.NetworkUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class LocationIdsSource {
    private final HotellookService api;

    public LocationIdsSource(HotellookService api) {
        this.api = api;
    }

    public Observable<List<Integer>> observe(SearchParams searchParams) {
        if (!needToSearchForNearestLocations(searchParams)) {
            return createObservableFromId(searchParams.locationId());
        }
        Timber.m755i("Start loading ids by location: %s", searchParams.location());
        return createNearestLocationObservable(searchParams.location()).map(LocationIdsSource$$Lambda$1.lambdaFactory$(this)).map(LocationIdsSource$$Lambda$2.lambdaFactory$(this, searchParams)).doOnNext(LocationIdsSource$$Lambda$3.lambdaFactory$()).doOnError(LocationIdsSource$$Lambda$4.lambdaFactory$(this));
    }

    /* synthetic */ List lambda$observe$0(List geoLocations) {
        return geoLocationsToIdsList(geoLocations);
    }

    /* synthetic */ List lambda$observe$1(SearchParams searchParams, List ids) {
        return addSearchParamsIdIfMissed(ids, searchParams.locationId());
    }

    /* synthetic */ void lambda$observe$3(Throwable error) {
        logError(error);
    }

    private boolean needToSearchForNearestLocations(SearchParams searchParams) {
        return searchParams.isDestinationTypeUserLocation() || searchParams.isDestinationTypeNearbyCities() || searchParams.isDestinationTypeMapPoint();
    }

    private List<Integer> addSearchParamsIdIfMissed(List<Integer> ids, int searchParamsId) {
        if (!(new HashSet(ids).contains(Integer.valueOf(searchParamsId)) || searchParamsId == -1)) {
            ids.add(Integer.valueOf(searchParamsId));
        }
        return ids;
    }

    private void logError(Throwable error) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading nearest locations: %s", error.getMessage());
            return;
        }
        Timber.m753e("Error while loading nearest locations: %s", error.getMessage());
    }

    @NonNull
    private Observable<List<Integer>> createObservableFromId(int locationId) {
        List<Integer> ids = new ArrayList(1);
        ids.add(Integer.valueOf(locationId));
        return Observable.just(ids);
    }

    @NonNull
    private List<Integer> geoLocationsToIdsList(List<GeoLocationData> geoLocations) {
        List<Integer> ids = new ArrayList();
        for (GeoLocationData geoLocation : geoLocations) {
            ids.add(Integer.valueOf(geoLocation.getId()));
        }
        return ids;
    }

    private Observable<List<GeoLocationData>> createNearestLocationObservable(Location location) {
        return this.api.locationByGeo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), HotellookService.LOCATION_ANY, MixPanelParams.AUTO, AndroidUtils.getLanguage());
    }
}
