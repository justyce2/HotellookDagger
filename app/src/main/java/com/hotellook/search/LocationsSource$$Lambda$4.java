package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action0;

final /* synthetic */ class LocationsSource$$Lambda$4 implements Action0 {
    private final LocationsSource arg$1;
    private final List arg$2;

    private LocationsSource$$Lambda$4(LocationsSource locationsSource, List list) {
        this.arg$1 = locationsSource;
        this.arg$2 = list;
    }

    public static Action0 lambdaFactory$(LocationsSource locationsSource, List list) {
        return new LocationsSource$$Lambda$4(locationsSource, list);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$observe$1(this.arg$2);
    }
}
