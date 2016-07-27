package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchEngine$$Lambda$2 implements Action1 {
    private final SearchEngine arg$1;

    private SearchEngine$$Lambda$2(SearchEngine searchEngine) {
        this.arg$1 = searchEngine;
    }

    public static Action1 lambdaFactory$(SearchEngine searchEngine) {
        return new SearchEngine$$Lambda$2(searchEngine);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$makeSearch$1((SearchData) obj);
    }
}
