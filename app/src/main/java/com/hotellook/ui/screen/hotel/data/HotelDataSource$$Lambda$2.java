package com.hotellook.ui.screen.hotel.data;

import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$2 implements Func1 {
    private static final HotelDataSource$$Lambda$2 instance;

    static {
        instance = new HotelDataSource$$Lambda$2();
    }

    private HotelDataSource$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((HotelDetailData) obj).getLocationId());
    }
}
