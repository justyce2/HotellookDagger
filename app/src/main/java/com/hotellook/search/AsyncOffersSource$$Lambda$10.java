package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class AsyncOffersSource$$Lambda$10 implements Action1 {
    private final AsyncOffersSource arg$1;
    private final SearchParams arg$2;

    private AsyncOffersSource$$Lambda$10(AsyncOffersSource asyncOffersSource, SearchParams searchParams) {
        this.arg$1 = asyncOffersSource;
        this.arg$2 = searchParams;
    }

    public static Action1 lambdaFactory$(AsyncOffersSource asyncOffersSource, SearchParams searchParams) {
        return new AsyncOffersSource$$Lambda$10(asyncOffersSource, searchParams);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createReadyOffersObservable$9(this.arg$2, (Throwable) obj);
    }
}
