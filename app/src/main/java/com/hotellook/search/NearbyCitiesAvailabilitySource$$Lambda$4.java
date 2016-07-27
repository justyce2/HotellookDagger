package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class NearbyCitiesAvailabilitySource$$Lambda$4 implements Action1 {
    private final NearbyCitiesAvailabilitySource arg$1;

    private NearbyCitiesAvailabilitySource$$Lambda$4(NearbyCitiesAvailabilitySource nearbyCitiesAvailabilitySource) {
        this.arg$1 = nearbyCitiesAvailabilitySource;
    }

    public static Action1 lambdaFactory$(NearbyCitiesAvailabilitySource nearbyCitiesAvailabilitySource) {
        return new NearbyCitiesAvailabilitySource$$Lambda$4(nearbyCitiesAvailabilitySource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$3((Throwable) obj);
    }
}
