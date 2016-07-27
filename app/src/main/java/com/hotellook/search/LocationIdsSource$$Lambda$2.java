package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class LocationIdsSource$$Lambda$2 implements Func1 {
    private final LocationIdsSource arg$1;
    private final SearchParams arg$2;

    private LocationIdsSource$$Lambda$2(LocationIdsSource locationIdsSource, SearchParams searchParams) {
        this.arg$1 = locationIdsSource;
        this.arg$2 = searchParams;
    }

    public static Func1 lambdaFactory$(LocationIdsSource locationIdsSource, SearchParams searchParams) {
        return new LocationIdsSource$$Lambda$2(locationIdsSource, searchParams);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observe$1(this.arg$2, (List) obj);
    }
}
