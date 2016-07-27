package com.hotellook.ui.screen.hotel.data;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class HotelDataSource$$Lambda$8 implements Action1 {
    private static final HotelDataSource$$Lambda$8 instance;

    static {
        instance = new HotelDataSource$$Lambda$8();
    }

    private HotelDataSource$$Lambda$8() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("Error while loading hotel data", new Object[0]);
    }
}
