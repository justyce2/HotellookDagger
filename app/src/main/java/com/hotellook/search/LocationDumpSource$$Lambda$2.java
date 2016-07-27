package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class LocationDumpSource$$Lambda$2 implements Action1 {
    private final LocationDumpSource arg$1;

    private LocationDumpSource$$Lambda$2(LocationDumpSource locationDumpSource) {
        this.arg$1 = locationDumpSource;
    }

    public static Action1 lambdaFactory$(LocationDumpSource locationDumpSource) {
        return new LocationDumpSource$$Lambda$2(locationDumpSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logLoadedResult((LocationDumps) obj);
    }
}
