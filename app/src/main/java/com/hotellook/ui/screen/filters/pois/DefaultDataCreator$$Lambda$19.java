package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$19 implements Func1 {
    private final CityInfo arg$1;

    private DefaultDataCreator$$Lambda$19(CityInfo cityInfo) {
        this.arg$1 = cityInfo;
    }

    public static Func1 lambdaFactory$(CityInfo cityInfo) {
        return new DefaultDataCreator$$Lambda$19(cityInfo);
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiWithCityId((Poi) obj, this.arg$1.getId());
    }
}
