package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.utils.LocationUtils;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$2 implements Func1 {
    private static final DefaultDataCreator$$Lambda$2 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$2();
    }

    private DefaultDataCreator$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return LocationUtils.from(((Poi) obj).getLocation());
    }
}
