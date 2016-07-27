package com.hotellook.ui.screen.hotel.data;

import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDataSource$$Lambda$25 implements Action1 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$25(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Action1 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$25(hotelDataSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeHotelDetail$22((HotelDetailData) obj);
    }
}
