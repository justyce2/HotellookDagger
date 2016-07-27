package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;
import timber.log.Timber;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$8 implements Action0 {
    private static final LastKnownLocationProvider$$Lambda$8 instance;

    static {
        instance = new LastKnownLocationProvider$$Lambda$8();
    }

    private LastKnownLocationProvider$$Lambda$8() {
    }

    public static Action0 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call() {
        Timber.m751d("Fine location requested", new Object[0]);
    }
}
