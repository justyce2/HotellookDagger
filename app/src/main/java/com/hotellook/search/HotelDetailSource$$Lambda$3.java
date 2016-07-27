package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class HotelDetailSource$$Lambda$3 implements Action0 {
    private final HotelDetailSource arg$1;
    private final long arg$2;

    private HotelDetailSource$$Lambda$3(HotelDetailSource hotelDetailSource, long j) {
        this.arg$1 = hotelDetailSource;
        this.arg$2 = j;
    }

    public static Action0 lambdaFactory$(HotelDetailSource hotelDetailSource, long j) {
        return new HotelDetailSource$$Lambda$3(hotelDetailSource, j);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$observe$1(this.arg$2);
    }
}
