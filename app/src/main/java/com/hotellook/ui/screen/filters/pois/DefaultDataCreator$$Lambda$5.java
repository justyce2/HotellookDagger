package com.hotellook.ui.screen.filters.pois;

import com.hotellook.ui.screen.filters.pois.listitems.PoiListItemBinder;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$5 implements Func1 {
    private static final DefaultDataCreator$$Lambda$5 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$5();
    }

    private DefaultDataCreator$$Lambda$5() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiListItemBinder(((PoiWithCityId) obj).cityId, ((PoiWithCityId) obj).poi);
    }
}
