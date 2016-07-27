package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class LocationIdsSource$$Lambda$4 implements Action1 {
    private final LocationIdsSource arg$1;

    private LocationIdsSource$$Lambda$4(LocationIdsSource locationIdsSource) {
        this.arg$1 = locationIdsSource;
    }

    public static Action1 lambdaFactory$(LocationIdsSource locationIdsSource) {
        return new LocationIdsSource$$Lambda$4(locationIdsSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$3((Throwable) obj);
    }
}
