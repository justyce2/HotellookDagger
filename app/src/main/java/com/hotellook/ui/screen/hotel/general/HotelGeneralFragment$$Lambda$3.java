package com.hotellook.ui.screen.hotel.general;

import android.util.Pair;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class HotelGeneralFragment$$Lambda$3 implements Func2 {
    private static final HotelGeneralFragment$$Lambda$3 instance;

    static {
        instance = new HotelGeneralFragment$$Lambda$3();
    }

    private HotelGeneralFragment$$Lambda$3() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return new Pair((BasicHotelData) obj, (CityInfo) obj2);
    }
}
