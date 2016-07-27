package com.hotellook.ui.screen.searchprogress;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class SearchProgressFragment$$Lambda$7 implements Action1 {
    private static final SearchProgressFragment$$Lambda$7 instance;

    static {
        instance = new SearchProgressFragment$$Lambda$7();
    }

    private SearchProgressFragment$$Lambda$7() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m752d((Throwable) obj, "Failed to observe search start", new Object[0]);
    }
}
