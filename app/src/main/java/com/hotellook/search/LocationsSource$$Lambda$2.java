package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class LocationsSource$$Lambda$2 implements Action1 {
    private final LocationsSource arg$1;

    private LocationsSource$$Lambda$2(LocationsSource locationsSource) {
        this.arg$1 = locationsSource;
    }

    public static Action1 lambdaFactory$(LocationsSource locationsSource) {
        return new LocationsSource$$Lambda$2(locationsSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logLoadedResult((Locations) obj);
    }
}
