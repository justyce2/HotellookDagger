package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class NearestLocationsSource$$Lambda$2 implements Action1 {
    private final NearestLocationsSource arg$1;

    private NearestLocationsSource$$Lambda$2(NearestLocationsSource nearestLocationsSource) {
        this.arg$1 = nearestLocationsSource;
    }

    public static Action1 lambdaFactory$(NearestLocationsSource nearestLocationsSource) {
        return new NearestLocationsSource$$Lambda$2(nearestLocationsSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logError((Throwable) obj);
    }
}
