package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action0;

final /* synthetic */ class LocationDumpSource$$Lambda$4 implements Action0 {
    private final LocationDumpSource arg$1;
    private final List arg$2;

    private LocationDumpSource$$Lambda$4(LocationDumpSource locationDumpSource, List list) {
        this.arg$1 = locationDumpSource;
        this.arg$2 = list;
    }

    public static Action0 lambdaFactory$(LocationDumpSource locationDumpSource, List list) {
        return new LocationDumpSource$$Lambda$4(locationDumpSource, list);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$observe$2(this.arg$2);
    }
}
