package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Map;
import rx.functions.Func1;

final /* synthetic */ class LocationDumpSource$$Lambda$1 implements Func1 {
    private static final LocationDumpSource$$Lambda$1 instance;

    static {
        instance = new LocationDumpSource$$Lambda$1();
    }

    private LocationDumpSource$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return LocationDumpSource.lambda$observe$0((Map) obj);
    }
}
