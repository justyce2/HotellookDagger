package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.concurrent.Callable;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$11 implements Callable {
    private final LastKnownLocationProvider arg$1;

    private LastKnownLocationProvider$$Lambda$11(LastKnownLocationProvider lastKnownLocationProvider) {
        this.arg$1 = lastKnownLocationProvider;
    }

    public static Callable lambdaFactory$(LastKnownLocationProvider lastKnownLocationProvider) {
        return new LastKnownLocationProvider$$Lambda$11(lastKnownLocationProvider);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$observeLocationPermission$13();
    }
}
