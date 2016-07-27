package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$6 implements Func1 {
    private final LocationRequestResolver arg$1;

    private LocationRequestResolver$$Lambda$6(LocationRequestResolver locationRequestResolver) {
        this.arg$1 = locationRequestResolver;
    }

    public static Func1 lambdaFactory$(LocationRequestResolver locationRequestResolver) {
        return new LocationRequestResolver$$Lambda$6(locationRequestResolver);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$createLocationPermissionObservable$8((Boolean) obj);
    }
}
