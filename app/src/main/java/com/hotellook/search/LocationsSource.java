package com.hotellook.search;

import com.hotellook.core.api.HotellookService;
import com.hotellook.utils.NetworkUtil;
import java.util.List;
import rx.Observable;
import timber.log.Timber;

public class LocationsSource {
    private final HotellookService api;

    public LocationsSource(HotellookService api) {
        this.api = api;
    }

    public Observable<Locations> observe(List<Integer> locationIds, String language) {
        return this.api.multipleCityInfo(language, convertLocationIds(locationIds)).map(LocationsSource$$Lambda$1.lambdaFactory$()).doOnNext(LocationsSource$$Lambda$2.lambdaFactory$(this)).doOnError(LocationsSource$$Lambda$3.lambdaFactory$(this, locationIds)).doOnSubscribe(LocationsSource$$Lambda$4.lambdaFactory$(this, locationIds));
    }

    /* synthetic */ void lambda$observe$0(List locationIds, Throwable error) {
        logError(error, locationIds);
    }

    /* synthetic */ void lambda$observe$1(List locationIds) {
        logStart(locationIds);
    }

    private String convertLocationIds(List<Integer> locationIds) {
        StringBuilder sb = new StringBuilder();
        for (Integer id : locationIds) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(id);
        }
        return sb.toString();
    }

    private void logLoadedResult(Locations locations) {
        Timber.m755i("Search. Loaded locations by ids: %s", locations);
    }

    private void logError(Throwable error, List<Integer> locationIds) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading locations by ids %s: %s", locationIds, error.getMessage());
            return;
        }
        Timber.m753e("Search. Error while loading locations by ids %s: %s", locationIds, error.getMessage());
    }

    private void logStart(List<Integer> locationIds) {
        Timber.m755i("Search. Start loading location infos for ids: %s", locationIds);
    }
}
