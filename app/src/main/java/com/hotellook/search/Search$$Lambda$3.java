package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class Search$$Lambda$3 implements Action1 {
    private final Search arg$1;

    private Search$$Lambda$3(Search search) {
        this.arg$1 = search;
    }

    public static Action1 lambdaFactory$(Search search) {
        return new Search$$Lambda$3(search);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onSearchFail((Throwable) obj);
    }
}
