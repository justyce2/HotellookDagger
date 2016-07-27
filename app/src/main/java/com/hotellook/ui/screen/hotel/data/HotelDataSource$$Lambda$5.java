package com.hotellook.ui.screen.hotel.data;

import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$5 implements Func1 {
    private static final HotelDataSource$$Lambda$5 instance;

    static {
        instance = new HotelDataSource$$Lambda$5();
    }

    private HotelDataSource$$Lambda$5() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new BasicHotelData((HotelDetailData) obj);
    }
}
