package com.hotellook.ui.screen.searchprogress;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class SearchProgressFragment$$Lambda$4 implements Action1 {
    private final SearchProgressFragment arg$1;

    private SearchProgressFragment$$Lambda$4(SearchProgressFragment searchProgressFragment) {
        this.arg$1 = searchProgressFragment;
    }

    public static Action1 lambdaFactory$(SearchProgressFragment searchProgressFragment) {
        return new SearchProgressFragment$$Lambda$4(searchProgressFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onViewCreated$3((List) obj);
    }
}
