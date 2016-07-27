package com.hotellook.ui.screen.hotel.data;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class HotelDataSource$$Lambda$7 implements Action1 {
    private static final HotelDataSource$$Lambda$7 instance;

    static {
        instance = new HotelDataSource$$Lambda$7();
    }

    private HotelDataSource$$Lambda$7() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("All hotel data loaded", new Object[0]);
    }
}
