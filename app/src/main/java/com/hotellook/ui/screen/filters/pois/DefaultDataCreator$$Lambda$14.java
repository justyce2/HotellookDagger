package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$14 implements Func1 {
    private static final DefaultDataCreator$$Lambda$14 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$14();
    }

    private DefaultDataCreator$$Lambda$14() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from((Iterable) (List) obj);
    }
}
