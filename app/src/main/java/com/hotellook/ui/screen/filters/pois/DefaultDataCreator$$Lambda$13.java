package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$13 implements Func1 {
    private static final DefaultDataCreator$$Lambda$13 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$13();
    }

    private DefaultDataCreator$$Lambda$13() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from((Iterable) (List) obj);
    }
}
