package com.hotellook.ui.screen.hotel.data;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDataSource$$Lambda$13 implements Action1 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$13(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Action1 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$13(hotelDataSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createLocationObservable$10((CityInfo) obj);
    }
}
