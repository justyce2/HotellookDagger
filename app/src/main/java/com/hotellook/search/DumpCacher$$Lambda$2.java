package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class DumpCacher$$Lambda$2 implements Action1 {
    private static final DumpCacher$$Lambda$2 instance;

    static {
        instance = new DumpCacher$$Lambda$2();
    }

    private DumpCacher$$Lambda$2() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m755i("Search. Hotels and locations dump pre cached.", new Object[0]);
    }
}
