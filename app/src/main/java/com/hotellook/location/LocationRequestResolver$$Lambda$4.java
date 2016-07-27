package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$4 implements Func1 {
    private static final LocationRequestResolver$$Lambda$4 instance;

    static {
        instance = new LocationRequestResolver$$Lambda$4();
    }

    private LocationRequestResolver$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return LocationRequestResolver.lambda$returnSettingsErrorOnFalse$6((Boolean) obj);
    }
}
