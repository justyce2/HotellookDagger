package com.hotellook.search;

import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class AsyncOffersSource$$Lambda$15 implements Action1 {
    private final AsyncOffersSource arg$1;

    private AsyncOffersSource$$Lambda$15(AsyncOffersSource asyncOffersSource) {
        this.arg$1 = asyncOffersSource;
    }

    public static Action1 lambdaFactory$(AsyncOffersSource asyncOffersSource) {
        return new AsyncOffersSource$$Lambda$15(asyncOffersSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createAsyncSearchLaunchObservable$13((SearchId) obj);
    }
}
