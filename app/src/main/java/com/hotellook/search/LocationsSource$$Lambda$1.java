package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Map;
import rx.functions.Func1;

final /* synthetic */ class LocationsSource$$Lambda$1 implements Func1 {
    private static final LocationsSource$$Lambda$1 instance;

    static {
        instance = new LocationsSource$$Lambda$1();
    }

    private LocationsSource$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new Locations((Map) obj);
    }
}
