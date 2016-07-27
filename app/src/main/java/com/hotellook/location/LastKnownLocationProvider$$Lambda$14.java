package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;
import timber.log.Timber;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$14 implements Action0 {
    private static final LastKnownLocationProvider$$Lambda$14 instance;

    static {
        instance = new LastKnownLocationProvider$$Lambda$14();
    }

    private LastKnownLocationProvider$$Lambda$14() {
    }

    public static Action0 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call() {
        Timber.m751d("Last known location request finished", new Object[0]);
    }
}
