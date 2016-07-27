package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$8 implements Func1 {
    private static final DefaultDataCreator$$Lambda$8 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$8();
    }

    private DefaultDataCreator$$Lambda$8() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Integer.valueOf(((Poi) obj).getId());
    }
}
