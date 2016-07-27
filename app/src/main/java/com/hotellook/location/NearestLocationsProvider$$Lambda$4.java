package com.hotellook.location;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class NearestLocationsProvider$$Lambda$4 implements Func1 {
    private final NearestLocationsSource arg$1;

    private NearestLocationsProvider$$Lambda$4(NearestLocationsSource nearestLocationsSource) {
        this.arg$1 = nearestLocationsSource;
    }

    public static Func1 lambdaFactory$(NearestLocationsSource nearestLocationsSource) {
        return new NearestLocationsProvider$$Lambda$4(nearestLocationsSource);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.observe((Location) obj);
    }
}
