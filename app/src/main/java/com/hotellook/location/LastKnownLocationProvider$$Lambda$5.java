package com.hotellook.location;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$5 implements Action1 {
    private final LastKnownLocationProvider arg$1;

    private LastKnownLocationProvider$$Lambda$5(LastKnownLocationProvider lastKnownLocationProvider) {
        this.arg$1 = lastKnownLocationProvider;
    }

    public static Action1 lambdaFactory$(LastKnownLocationProvider lastKnownLocationProvider) {
        return new LastKnownLocationProvider$$Lambda$5(lastKnownLocationProvider);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logError((Throwable) obj);
    }
}
