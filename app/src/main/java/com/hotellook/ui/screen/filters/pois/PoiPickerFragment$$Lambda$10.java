package com.hotellook.ui.screen.filters.pois;

import com.hotellook.ui.screen.filters.pois.listitems.PoiListItemBinder;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PoiPickerFragment$$Lambda$10 implements Func1 {
    private static final PoiPickerFragment$$Lambda$10 instance;

    static {
        instance = new PoiPickerFragment$$Lambda$10();
    }

    private PoiPickerFragment$$Lambda$10() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiListItemBinder(((PoiWithCityId) obj).cityId, ((PoiWithCityId) obj).poi);
    }
}
