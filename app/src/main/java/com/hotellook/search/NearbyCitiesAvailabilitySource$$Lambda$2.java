package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class NearbyCitiesAvailabilitySource$$Lambda$2 implements Func1 {
    private static final NearbyCitiesAvailabilitySource$$Lambda$2 instance;

    static {
        instance = new NearbyCitiesAvailabilitySource$$Lambda$2();
    }

    private NearbyCitiesAvailabilitySource$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return NearbyCitiesAvailabilitySource.lambda$observe$1((Integer) obj);
    }
}
