package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$18 implements Func1 {
    private final String arg$1;

    private DefaultDataCreator$$Lambda$18(String str) {
        this.arg$1 = str;
    }

    public static Func1 lambdaFactory$(String str) {
        return new DefaultDataCreator$$Lambda$18(str);
    }

    @Hidden
    public Object call(Object obj) {
        return Boolean.valueOf(this.arg$1.equals(((Poi) obj).getCategory()));
    }
}
