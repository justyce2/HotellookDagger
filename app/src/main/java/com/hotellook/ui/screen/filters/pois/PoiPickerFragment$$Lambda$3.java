package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class PoiPickerFragment$$Lambda$3 implements Action1 {
    private static final PoiPickerFragment$$Lambda$3 instance;

    static {
        instance = new PoiPickerFragment$$Lambda$3();
    }

    private PoiPickerFragment$$Lambda$3() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "Error on prepare poi selector data", new Object[0]);
    }
}
