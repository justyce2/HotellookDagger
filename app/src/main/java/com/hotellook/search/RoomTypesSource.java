package com.hotellook.search;

import com.hotellook.core.api.HotellookService;
import com.hotellook.utils.NetworkUtil;
import rx.Observable;
import timber.log.Timber;

public class RoomTypesSource {
    private final HotellookService api;

    public RoomTypesSource(HotellookService api) {
        this.api = api;
    }

    public Observable<RoomTypes> observe(String language) {
        return this.api.observeRoomTypes(language).map(RoomTypesSource$$Lambda$1.lambdaFactory$()).doOnNext(RoomTypesSource$$Lambda$2.lambdaFactory$(this)).doOnError(RoomTypesSource$$Lambda$3.lambdaFactory$(this));
    }

    private void logError(Throwable error) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Timber.m756w("Connection error while loading room types: %s", error.getMessage());
            return;
        }
        Timber.m753e("Error while loading room types: %s", error.getMessage());
    }

    private void logSuccess(RoomTypes roomTypes) {
        Timber.m755i("Room types loaded: %s", roomTypes);
    }
}
