package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class LocationRequestResolver$$Lambda$14 implements Action1 {
    private static final LocationRequestResolver$$Lambda$14 instance;

    static {
        instance = new LocationRequestResolver$$Lambda$14();
    }

    private LocationRequestResolver$$Lambda$14() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("Location permission results: %s", (Boolean) obj);
    }
}
