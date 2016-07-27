package com.hotellook.ui.screen.hotel;

import android.util.Pair;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class HotelFragment$$Lambda$1 implements Func2 {
    private static final HotelFragment$$Lambda$1 instance;

    static {
        instance = new HotelFragment$$Lambda$1();
    }

    private HotelFragment$$Lambda$1() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return new Pair((BasicHotelData) obj, (CityInfo) obj2);
    }
}
