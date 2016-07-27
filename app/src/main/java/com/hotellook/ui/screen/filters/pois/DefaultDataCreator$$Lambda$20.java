package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$20 implements Func1 {
    private static final DefaultDataCreator$$Lambda$20 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$20();
    }

    private DefaultDataCreator$$Lambda$20() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((PoiWithCityId) obj).poi.getId());
    }
}
