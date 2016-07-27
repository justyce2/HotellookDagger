package com.hotellook.location;

import com.google.android.gms.location.LocationSettingsResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$3 implements Func1 {
    private final LocationRequestResolver arg$1;

    private LocationRequestResolver$$Lambda$3(LocationRequestResolver locationRequestResolver) {
        this.arg$1 = locationRequestResolver;
    }

    public static Func1 lambdaFactory$(LocationRequestResolver locationRequestResolver) {
        return new LocationRequestResolver$$Lambda$3(locationRequestResolver);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$handleLocationSettingsResult$5((LocationSettingsResult) obj);
    }
}
