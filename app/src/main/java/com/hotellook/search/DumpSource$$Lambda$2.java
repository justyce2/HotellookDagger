package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DumpSource$$Lambda$2 implements Func1 {
    private static final DumpSource$$Lambda$2 instance;

    static {
        instance = new DumpSource$$Lambda$2();
    }

    private DumpSource$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((Locations) obj).countHotels());
    }
}
