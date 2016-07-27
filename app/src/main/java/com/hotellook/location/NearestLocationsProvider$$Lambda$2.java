package com.hotellook.location;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class NearestLocationsProvider$$Lambda$2 implements Action1 {
    private final NearestLocationsProvider arg$1;

    private NearestLocationsProvider$$Lambda$2(NearestLocationsProvider nearestLocationsProvider) {
        this.arg$1 = nearestLocationsProvider;
    }

    public static Action1 lambdaFactory$(NearestLocationsProvider nearestLocationsProvider) {
        return new NearestLocationsProvider$$Lambda$2(nearestLocationsProvider);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((Location) obj);
    }
}
