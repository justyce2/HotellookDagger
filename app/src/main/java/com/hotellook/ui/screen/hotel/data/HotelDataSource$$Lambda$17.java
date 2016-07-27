package com.hotellook.ui.screen.hotel.data;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class HotelDataSource$$Lambda$17 implements Action0 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$17(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Action0 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$17(hotelDataSource);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$createOffersObservable$14();
    }
}
