package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;
import timber.log.Timber;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$12 implements Action0 {
    private static final LastKnownLocationProvider$$Lambda$12 instance;

    static {
        instance = new LastKnownLocationProvider$$Lambda$12();
    }

    private LastKnownLocationProvider$$Lambda$12() {
    }

    public static Action0 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call() {
        Timber.m751d("Last known location requested", new Object[0]);
    }
}
