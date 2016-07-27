package com.hotellook.ui.screen.hotel.data;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$3 implements Func1 {
    private final HotelDataSource arg$1;
    private final String arg$2;

    private HotelDataSource$$Lambda$3(HotelDataSource hotelDataSource, String str) {
        this.arg$1 = hotelDataSource;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(HotelDataSource hotelDataSource, String str) {
        return new HotelDataSource$$Lambda$3(hotelDataSource, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observeFromIntent$1(this.arg$2, (Integer) obj);
    }
}
