package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.concurrent.Callable;

final /* synthetic */ class LocationRequestResolver$$Lambda$5 implements Callable {
    private final LocationRequestResolver arg$1;

    private LocationRequestResolver$$Lambda$5(LocationRequestResolver locationRequestResolver) {
        this.arg$1 = locationRequestResolver;
    }

    public static Callable lambdaFactory$(LocationRequestResolver locationRequestResolver) {
        return new LocationRequestResolver$$Lambda$5(locationRequestResolver);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$createLocationPermissionObservable$7();
    }
}
