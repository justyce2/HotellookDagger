package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class LocationIdsSource$$Lambda$3 implements Action1 {
    private static final LocationIdsSource$$Lambda$3 instance;

    static {
        instance = new LocationIdsSource$$Lambda$3();
    }

    private LocationIdsSource$$Lambda$3() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m755i("Location ids loaded: %s", (List) obj);
    }
}
