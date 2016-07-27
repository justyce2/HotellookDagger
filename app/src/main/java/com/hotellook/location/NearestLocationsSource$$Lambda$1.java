package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class NearestLocationsSource$$Lambda$1 implements Action1 {
    private final NearestLocationsSource arg$1;

    private NearestLocationsSource$$Lambda$1(NearestLocationsSource nearestLocationsSource) {
        this.arg$1 = nearestLocationsSource;
    }

    public static Action1 lambdaFactory$(NearestLocationsSource nearestLocationsSource) {
        return new NearestLocationsSource$$Lambda$1(nearestLocationsSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logNext((List) obj);
    }
}
