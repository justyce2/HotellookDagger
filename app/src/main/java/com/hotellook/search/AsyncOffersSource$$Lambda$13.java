package com.hotellook.search;

import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class AsyncOffersSource$$Lambda$13 implements Action1 {
    private static final AsyncOffersSource$$Lambda$13 instance;

    static {
        instance = new AsyncOffersSource$$Lambda$13();
    }

    private AsyncOffersSource$$Lambda$13() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m751d("Search started. Waiting for results from gates: %s", ((SearchId) obj).gates());
    }
}
