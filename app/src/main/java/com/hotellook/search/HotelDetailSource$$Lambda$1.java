package com.hotellook.search;

import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDetailSource$$Lambda$1 implements Action1 {
    private final HotelDetailSource arg$1;

    private HotelDetailSource$$Lambda$1(HotelDetailSource hotelDetailSource) {
        this.arg$1 = hotelDetailSource;
    }

    public static Action1 lambdaFactory$(HotelDetailSource hotelDetailSource) {
        return new HotelDetailSource$$Lambda$1(hotelDetailSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logLoadedResult((HotelDetailData) obj);
    }
}
