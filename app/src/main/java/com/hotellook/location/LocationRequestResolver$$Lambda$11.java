package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class LocationRequestResolver$$Lambda$11 implements Action1 {
    private static final LocationRequestResolver$$Lambda$11 instance;

    static {
        instance = new LocationRequestResolver$$Lambda$11();
    }

    private LocationRequestResolver$$Lambda$11() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("Google play services availability: %s", (Boolean) obj);
    }
}
