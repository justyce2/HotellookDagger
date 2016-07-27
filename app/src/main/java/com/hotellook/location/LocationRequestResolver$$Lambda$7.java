package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$7 implements Func1 {
    private static final LocationRequestResolver$$Lambda$7 instance;

    static {
        instance = new LocationRequestResolver$$Lambda$7();
    }

    private LocationRequestResolver$$Lambda$7() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return LocationRequestResolver.lambda$createLocationPermissionObservable$9((Boolean) obj);
    }
}
