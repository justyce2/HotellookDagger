package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class LocationsSource$$Lambda$3 implements Action1 {
    private final LocationsSource arg$1;
    private final List arg$2;

    private LocationsSource$$Lambda$3(LocationsSource locationsSource, List list) {
        this.arg$1 = locationsSource;
        this.arg$2 = list;
    }

    public static Action1 lambdaFactory$(LocationsSource locationsSource, List list) {
        return new LocationsSource$$Lambda$3(locationsSource, list);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$0(this.arg$2, (Throwable) obj);
    }
}
