package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PoiPickerFragment$$Lambda$9 implements Func1 {
    private static final PoiPickerFragment$$Lambda$9 instance;

    static {
        instance = new PoiPickerFragment$$Lambda$9();
    }

    private PoiPickerFragment$$Lambda$9() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((PoiWithCityId) obj).poi.getId());
    }
}
