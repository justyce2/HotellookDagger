package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDetailSource$$Lambda$2 implements Action1 {
    private final HotelDetailSource arg$1;
    private final long arg$2;

    private HotelDetailSource$$Lambda$2(HotelDetailSource hotelDetailSource, long j) {
        this.arg$1 = hotelDetailSource;
        this.arg$2 = j;
    }

    public static Action1 lambdaFactory$(HotelDetailSource hotelDetailSource, long j) {
        return new HotelDetailSource$$Lambda$2(hotelDetailSource, j);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$0(this.arg$2, (Throwable) obj);
    }
}
