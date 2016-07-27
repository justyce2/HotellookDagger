package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.Observable.Transformer;

final /* synthetic */ class LocationRequestResolver$$Lambda$8 implements Transformer {
    private final LocationRequestResolver arg$1;

    private LocationRequestResolver$$Lambda$8(LocationRequestResolver locationRequestResolver) {
        this.arg$1 = locationRequestResolver;
    }

    public static Transformer lambdaFactory$(LocationRequestResolver locationRequestResolver) {
        return new LocationRequestResolver$$Lambda$8(locationRequestResolver);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$ensureGooglePlayServices$12((Observable) obj);
    }
}
