package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class LocationDumpSource$$Lambda$3 implements Action1 {
    private final LocationDumpSource arg$1;
    private final List arg$2;

    private LocationDumpSource$$Lambda$3(LocationDumpSource locationDumpSource, List list) {
        this.arg$1 = locationDumpSource;
        this.arg$2 = list;
    }

    public static Action1 lambdaFactory$(LocationDumpSource locationDumpSource, List list) {
        return new LocationDumpSource$$Lambda$3(locationDumpSource, list);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$1(this.arg$2, (Throwable) obj);
    }
}
