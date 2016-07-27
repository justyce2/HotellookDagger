package com.hotellook.search;

import com.hotellook.api.RequestFlags;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class AsyncOffersSource$$Lambda$7 implements Func1 {
    private final AsyncOffersSource arg$1;
    private final SearchParams arg$2;
    private final RequestFlags arg$3;

    private AsyncOffersSource$$Lambda$7(AsyncOffersSource asyncOffersSource, SearchParams searchParams, RequestFlags requestFlags) {
        this.arg$1 = asyncOffersSource;
        this.arg$2 = searchParams;
        this.arg$3 = requestFlags;
    }

    public static Func1 lambdaFactory$(AsyncOffersSource asyncOffersSource, SearchParams searchParams, RequestFlags requestFlags) {
        return new AsyncOffersSource$$Lambda$7(asyncOffersSource, searchParams, requestFlags);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$createReadyOffersObservable$6(this.arg$2, this.arg$3, (SearchLaunchData) obj);
    }
}
