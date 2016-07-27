package com.hotellook.ui.screen.searchform;

import com.google.android.gms.location.LocationRequest;
import com.hotellook.HotellookApplication;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class SearchFormFragment$$Lambda$3 implements Func1 {
    private final LocationRequest arg$1;

    private SearchFormFragment$$Lambda$3(LocationRequest locationRequest) {
        this.arg$1 = locationRequest;
    }

    public static Func1 lambdaFactory$(LocationRequest locationRequest) {
        return new SearchFormFragment$$Lambda$3(locationRequest);
    }

    @Hidden
    public Object call(Object obj) {
        return HotellookApplication.getApp().getComponent().locationProvider().getUpdatedLocation(this.arg$1);
    }
}
