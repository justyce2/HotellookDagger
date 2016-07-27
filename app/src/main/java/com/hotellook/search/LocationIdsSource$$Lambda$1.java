package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class LocationIdsSource$$Lambda$1 implements Func1 {
    private final LocationIdsSource arg$1;

    private LocationIdsSource$$Lambda$1(LocationIdsSource locationIdsSource) {
        this.arg$1 = locationIdsSource;
    }

    public static Func1 lambdaFactory$(LocationIdsSource locationIdsSource) {
        return new LocationIdsSource$$Lambda$1(locationIdsSource);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observe$0((List) obj);
    }
}
