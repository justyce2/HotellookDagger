package com.hotellook.location;

import com.google.android.gms.location.LocationRequest;
import java.lang.invoke.LambdaForm.Hidden;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.functions.Func1;

final /* synthetic */ class LocationRequestResolver$$Lambda$12 implements Func1 {
    private final LocationRequestResolver arg$1;
    private final LocationRequest arg$2;
    private final ReactiveLocationProvider arg$3;

    private LocationRequestResolver$$Lambda$12(LocationRequestResolver locationRequestResolver, LocationRequest locationRequest, ReactiveLocationProvider reactiveLocationProvider) {
        this.arg$1 = locationRequestResolver;
        this.arg$2 = locationRequest;
        this.arg$3 = reactiveLocationProvider;
    }

    public static Func1 lambdaFactory$(LocationRequestResolver locationRequestResolver, LocationRequest locationRequest, ReactiveLocationProvider reactiveLocationProvider) {
        return new LocationRequestResolver$$Lambda$12(locationRequestResolver, locationRequest, reactiveLocationProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$null$3(this.arg$2, this.arg$3, (Boolean) obj);
    }
}
