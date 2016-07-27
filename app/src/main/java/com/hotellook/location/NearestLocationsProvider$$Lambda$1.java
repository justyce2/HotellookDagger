package com.hotellook.location;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class NearestLocationsProvider$$Lambda$1 implements Func1 {
    private final NearestLocationsProvider arg$1;

    private NearestLocationsProvider$$Lambda$1(NearestLocationsProvider nearestLocationsProvider) {
        this.arg$1 = nearestLocationsProvider;
    }

    public static Func1 lambdaFactory$(NearestLocationsProvider nearestLocationsProvider) {
        return new NearestLocationsProvider$$Lambda$1(nearestLocationsProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return Boolean.valueOf(this.arg$1.distanceChangedSufficiently((Location) obj));
    }
}
