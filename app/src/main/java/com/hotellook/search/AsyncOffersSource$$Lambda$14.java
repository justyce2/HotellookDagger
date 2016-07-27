package com.hotellook.search;

import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class AsyncOffersSource$$Lambda$14 implements Action1 {
    private final AsyncOffersSource arg$1;

    private AsyncOffersSource$$Lambda$14(AsyncOffersSource asyncOffersSource) {
        this.arg$1 = asyncOffersSource;
    }

    public static Action1 lambdaFactory$(AsyncOffersSource asyncOffersSource) {
        return new AsyncOffersSource$$Lambda$14(asyncOffersSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createAsyncSearchLaunchObservable$12((SearchId) obj);
    }
}
