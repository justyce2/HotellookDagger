package com.hotellook.search;

import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.common.District;
import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.core.api.pojo.hotelsdump.HotelData;
import com.hotellook.core.api.pojo.hotelsdump.HotelsDump;
import com.hotellook.core.api.pojo.hotelsdump.SeasonDates;
import com.hotellook.utils.NetworkUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import timber.log.Timber;

public class LocationDumpSource {
    public static final int MAX_PRICELESS_COUNT = 100;
    public static final int REAL_RATING = 1;
    private final HotellookService api;

    public LocationDumpSource(HotellookService api) {
        this.api = api;
    }

    public Observable<LocationDumps> observe(List<Integer> locationIds, String language, int hotelsCount) {
        return this.api.multiLocationHotelsDump(language, convertLocationIds(locationIds), hotelsCount < MAX_PRICELESS_COUNT ? REAL_RATING : 0, REAL_RATING).map(LocationDumpSource$$Lambda$1.lambdaFactory$()).doOnNext(LocationDumpSource$$Lambda$2.lambdaFactory$(this)).doOnError(LocationDumpSource$$Lambda$3.lambdaFactory$(this, locationIds)).doOnSubscribe(LocationDumpSource$$Lambda$4.lambdaFactory$(this, locationIds));
    }

    static /* synthetic */ LocationDumps lambda$observe$0(Map locationDumpMap) {
        Map<Integer, List<HotelData>> hotels = new HashMap(locationDumpMap.size());
        Map<Integer, List<Poi>> pois = new HashMap(locationDumpMap.size());
        Map<Integer, List<District>> districts = new HashMap(locationDumpMap.size());
        Map<Integer, Map<String, Map<String, SeasonDates>>> seasons = new HashMap(locationDumpMap.size());
        for (Integer locationId : locationDumpMap.keySet()) {
            hotels.put(locationId, ((HotelsDump) locationDumpMap.get(locationId)).getHotels());
            pois.put(locationId, ((HotelsDump) locationDumpMap.get(locationId)).getPois());
            districts.put(locationId, ((HotelsDump) locationDumpMap.get(locationId)).getDistricts());
            seasons.put(locationId, ((HotelsDump) locationDumpMap.get(locationId)).getSeasons());
        }
        return new LocationDumps(new Hotels(hotels), new Pois(pois), new Districts(districts), new Seasons(seasons));
    }

    /* synthetic */ void lambda$observe$1(List locationIds, Throwable error) {
        logError(error, locationIds);
    }

    /* synthetic */ void lambda$observe$2(List locationIds) {
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

    private void logLoadedResult(LocationDumps locationDumps) {
        Object[] objArr = new Object[REAL_RATING];
        objArr[0] = locationDumps.hotels().toMap().keySet();
        Timber.m755i("Loaded location dump for location ids: %s", objArr);
    }

    private void logError(Throwable error, List<Integer> locationIds) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading location dump for ids %s: %s", locationIds, error.getMessage());
            return;
        }
        Timber.m753e("Error while loading location dump for location ids %s: %s", locationIds, error.getMessage());
    }

    private void logStart(List<Integer> locationIds) {
        Object[] objArr = new Object[REAL_RATING];
        objArr[0] = locationIds;
        Timber.m755i("Start loading location dump for ids: %s", objArr);
    }
}
