package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$13 implements Func1 {
    private final LocationRequestResolver arg$1;

    private LocationRequestResolver$$Lambda$13(LocationRequestResolver locationRequestResolver) {
        this.arg$1 = locationRequestResolver;
    }

    public static Func1 lambdaFactory$(LocationRequestResolver locationRequestResolver) {
        return new LocationRequestResolver$$Lambda$13(locationRequestResolver);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$null$0((Boolean) obj);
    }
}
