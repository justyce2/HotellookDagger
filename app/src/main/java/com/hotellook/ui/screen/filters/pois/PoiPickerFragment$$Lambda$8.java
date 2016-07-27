package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PoiPickerFragment$$Lambda$8 implements Func1 {
    private final Integer arg$1;

    private PoiPickerFragment$$Lambda$8(Integer num) {
        this.arg$1 = num;
    }

    public static Func1 lambdaFactory$(Integer num) {
        return new PoiPickerFragment$$Lambda$8(num);
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiWithCityId((Poi) obj, this.arg$1.intValue());
    }
}
