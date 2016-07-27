package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class AsyncOffersSource$$Lambda$5 implements Action1 {
    private final AsyncOffersSource arg$1;

    private AsyncOffersSource$$Lambda$5(AsyncOffersSource asyncOffersSource) {
        this.arg$1 = asyncOffersSource;
    }

    public static Action1 lambdaFactory$(AsyncOffersSource asyncOffersSource) {
        return new AsyncOffersSource$$Lambda$5(asyncOffersSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createOffersObservable$4((Offers) obj);
    }
}
