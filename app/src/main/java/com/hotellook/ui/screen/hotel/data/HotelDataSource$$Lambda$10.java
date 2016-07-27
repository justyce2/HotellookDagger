package com.hotellook.ui.screen.hotel.data;

import android.support.v4.util.Pair;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDataSource$$Lambda$10 implements Action1 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$10(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Action1 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$10(hotelDataSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createFullDataObservable$7((Pair) obj);
    }
}
