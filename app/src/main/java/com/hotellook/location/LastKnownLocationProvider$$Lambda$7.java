package com.hotellook.location;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LastKnownLocationProvider$$Lambda$7 implements Func1 {
    private final LastKnownLocationProvider arg$1;

    private LastKnownLocationProvider$$Lambda$7(LastKnownLocationProvider lastKnownLocationProvider) {
        this.arg$1 = lastKnownLocationProvider;
    }

    public static Func1 lambdaFactory$(LastKnownLocationProvider lastKnownLocationProvider) {
        return new LastKnownLocationProvider$$Lambda$7(lastKnownLocationProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observeLastKnownLocation$9((Location) obj);
    }
}
