package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class DumpCacher$$Lambda$3 implements Action1 {
    private static final DumpCacher$$Lambda$3 instance;

    static {
        instance = new DumpCacher$$Lambda$3();
    }

    private DumpCacher$$Lambda$3() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "Search. Error while pre caching full dump", new Object[0]);
    }
}
