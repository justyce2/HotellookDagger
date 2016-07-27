package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;
import timber.log.Timber;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$10 implements Action0 {
    private static final LastKnownLocationProvider$$Lambda$10 instance;

    static {
        instance = new LastKnownLocationProvider$$Lambda$10();
    }

    private LastKnownLocationProvider$$Lambda$10() {
    }

    public static Action0 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call() {
        Timber.m751d("Fine location request finished", new Object[0]);
    }
}
