package com.hotellook.ui.screen.searchprogress;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchProgressFragment$$Lambda$2 implements Action1 {
    private final SearchProgressFragment arg$1;

    private SearchProgressFragment$$Lambda$2(SearchProgressFragment searchProgressFragment) {
        this.arg$1 = searchProgressFragment;
    }

    public static Action1 lambdaFactory$(SearchProgressFragment searchProgressFragment) {
        return new SearchProgressFragment$$Lambda$2(searchProgressFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onViewCreated$1((Throwable) obj);
    }
}
