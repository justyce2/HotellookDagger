package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$10 implements Func1 {
    private final int arg$1;

    private DefaultDataCreator$$Lambda$10(int i) {
        this.arg$1 = i;
    }

    public static Func1 lambdaFactory$(int i) {
        return new DefaultDataCreator$$Lambda$10(i);
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiWithCityId((Poi) obj, this.arg$1);
    }
}
