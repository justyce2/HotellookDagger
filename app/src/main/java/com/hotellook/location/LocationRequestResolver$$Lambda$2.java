package com.hotellook.location;

import com.google.android.gms.location.LocationRequest;
import java.lang.invoke.LambdaForm.Hidden;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Observable.Transformer;

final /* synthetic */ class LocationRequestResolver$$Lambda$2 implements Transformer {
    private final LocationRequestResolver arg$1;
    private final LocationRequest arg$2;
    private final ReactiveLocationProvider arg$3;

    private LocationRequestResolver$$Lambda$2(LocationRequestResolver locationRequestResolver, LocationRequest locationRequest, ReactiveLocationProvider reactiveLocationProvider) {
        this.arg$1 = locationRequestResolver;
        this.arg$2 = locationRequest;
        this.arg$3 = reactiveLocationProvider;
    }

    public static Transformer lambdaFactory$(LocationRequestResolver locationRequestResolver, LocationRequest locationRequest, ReactiveLocationProvider reactiveLocationProvider) {
        return new LocationRequestResolver$$Lambda$2(locationRequestResolver, locationRequest, reactiveLocationProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$ensureLocationSettings$4(this.arg$2, this.arg$3, (Observable) obj);
    }
}
