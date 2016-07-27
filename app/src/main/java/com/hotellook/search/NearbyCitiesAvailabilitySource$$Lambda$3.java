package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class NearbyCitiesAvailabilitySource$$Lambda$3 implements Action1 {
    private static final NearbyCitiesAvailabilitySource$$Lambda$3 instance;

    static {
        instance = new NearbyCitiesAvailabilitySource$$Lambda$3();
    }

    private NearbyCitiesAvailabilitySource$$Lambda$3() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m755i("Nearby search availability: %s", (Boolean) obj);
    }
}
