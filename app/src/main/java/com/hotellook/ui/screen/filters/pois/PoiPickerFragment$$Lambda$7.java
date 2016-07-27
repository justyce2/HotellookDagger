package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PoiPickerFragment$$Lambda$7 implements Func1 {
    private final String arg$1;

    private PoiPickerFragment$$Lambda$7(String str) {
        this.arg$1 = str;
    }

    public static Func1 lambdaFactory$(String str) {
        return new PoiPickerFragment$$Lambda$7(str);
    }

    @Hidden
    public Object call(Object obj) {
        return Boolean.valueOf(((Poi) obj).getName().toLowerCase().contains(this.arg$1));
    }
}
