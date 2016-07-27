package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.Observable.Transformer;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$3 implements Transformer {
    private final LastKnownLocationProvider arg$1;

    private LastKnownLocationProvider$$Lambda$3(LastKnownLocationProvider lastKnownLocationProvider) {
        this.arg$1 = lastKnownLocationProvider;
    }

    public static Transformer lambdaFactory$(LastKnownLocationProvider lastKnownLocationProvider) {
        return new LastKnownLocationProvider$$Lambda$3(lastKnownLocationProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$ensureLocationPermission$4((Observable) obj);
    }
}
