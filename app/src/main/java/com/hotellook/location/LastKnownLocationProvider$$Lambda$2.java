package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$2 implements Func1 {
    private final LastKnownLocationProvider arg$1;

    private LastKnownLocationProvider$$Lambda$2(LastKnownLocationProvider lastKnownLocationProvider) {
        this.arg$1 = lastKnownLocationProvider;
    }

    public static Func1 lambdaFactory$(LastKnownLocationProvider lastKnownLocationProvider) {
        return new LastKnownLocationProvider$$Lambda$2(lastKnownLocationProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$handlePlayServicesAvailability$1((Boolean) obj);
    }
}
