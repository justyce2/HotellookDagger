package com.hotellook.location;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$13 implements Action1 {
    private static final LastKnownLocationProvider$$Lambda$13 instance;

    static {
        instance = new LastKnownLocationProvider$$Lambda$13();
    }

    private LastKnownLocationProvider$$Lambda$13() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("Last known location received: %s", (Location) obj);
    }
}
