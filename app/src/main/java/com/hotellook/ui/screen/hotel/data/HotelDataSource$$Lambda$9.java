package com.hotellook.ui.screen.hotel.data;

import android.support.v4.util.Pair;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.hoteldetail.HotelDetailData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class HotelDataSource$$Lambda$9 implements Func2 {
    private static final HotelDataSource$$Lambda$9 instance;

    static {
        instance = new HotelDataSource$$Lambda$9();
    }

    private HotelDataSource$$Lambda$9() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return new Pair((HotelDetailData) obj, (CityInfo) obj2);
    }
}
