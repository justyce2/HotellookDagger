package com.hotellook.ui.screen.hotel.data;

import com.hotellook.search.Discounts;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$19 implements Func1 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$19(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Func1 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$19(hotelDataSource);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$createOffersObservable$16((Discounts) obj);
    }
}
