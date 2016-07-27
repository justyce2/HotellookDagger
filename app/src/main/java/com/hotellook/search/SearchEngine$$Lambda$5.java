package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchEngine$$Lambda$5 implements Action1 {
    private final SearchEngine arg$1;

    private SearchEngine$$Lambda$5(SearchEngine searchEngine) {
        this.arg$1 = searchEngine;
    }

    public static Action1 lambdaFactory$(SearchEngine searchEngine) {
        return new SearchEngine$$Lambda$5(searchEngine);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeExchangeRate$4((Double) obj);
    }
}
