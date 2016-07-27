package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class AsyncOffersSource$$Lambda$2 implements Func1 {
    private final AsyncOffersSource arg$1;

    private AsyncOffersSource$$Lambda$2(AsyncOffersSource asyncOffersSource) {
        this.arg$1 = asyncOffersSource;
    }

    public static Func1 lambdaFactory$(AsyncOffersSource asyncOffersSource) {
        return new AsyncOffersSource$$Lambda$2(asyncOffersSource);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observeDiscounts$1((Offers) obj);
    }
}
