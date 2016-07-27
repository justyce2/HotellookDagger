package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class NearbyCitiesAvailabilitySource$$Lambda$1 implements Func1 {
    private static final NearbyCitiesAvailabilitySource$$Lambda$1 instance;

    static {
        instance = new NearbyCitiesAvailabilitySource$$Lambda$1();
    }

    private NearbyCitiesAvailabilitySource$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((List) obj).size());
    }
}
