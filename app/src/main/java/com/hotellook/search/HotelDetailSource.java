package com.hotellook.search;

import com.hotellook.core.api.HotellookService;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import com.hotellook.utils.NetworkUtil;
import rx.Observable;
import timber.log.Timber;

public class HotelDetailSource {
    public static final int REAL_RATING = 1;
    private final HotellookService api;

    public HotelDetailSource(HotellookService api) {
        this.api = api;
    }

    public Observable<HotelDetailData> observe(String language, long hotelId) {
        return this.api.hotelDetail(language, hotelId, REAL_RATING).doOnNext(HotelDetailSource$$Lambda$1.lambdaFactory$(this)).doOnError(HotelDetailSource$$Lambda$2.lambdaFactory$(this, hotelId)).doOnSubscribe(HotelDetailSource$$Lambda$3.lambdaFactory$(this, hotelId));
    }

    /* synthetic */ void lambda$observe$0(long hotelId, Throwable error) {
        logError(error, hotelId);
    }

    /* synthetic */ void lambda$observe$1(long hotelId) {
        logStart(hotelId);
    }

    private void logLoadedResult(HotelDetailData hotelDetailData) {
        Object[] objArr = new Object[REAL_RATING];
        objArr[0] = Long.valueOf(hotelDetailData.getId());
        Timber.m755i("Loaded hotel detail info for id: %s", objArr);
    }

    private void logError(Throwable error, long hotelId) {
        if (NetworkUtil.isRetrofitNetworkError(error)) {
            Object[] objArr = new Object[REAL_RATING];
            objArr[0] = error.getMessage();
            Timber.m756w("Connection error while loading detail hotel info: %s", objArr);
            return;
        }
        Timber.m753e("Error while loading detail hotel info for id %s: %s", Long.valueOf(hotelId), error.getMessage());
    }

    private void logStart(long hotelId) {
        Object[] objArr = new Object[REAL_RATING];
        objArr[0] = Long.valueOf(hotelId);
        Timber.m755i("Start loading detail hotel info for id %d", objArr);
    }
}
