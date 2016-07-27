package com.hotellook.ui.screen.searchprogress;

import com.hotellook.search.SearchData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchProgressFragment$$Lambda$1 implements Action1 {
    private final SearchProgressFragment arg$1;

    private SearchProgressFragment$$Lambda$1(SearchProgressFragment searchProgressFragment) {
        this.arg$1 = searchProgressFragment;
    }

    public static Action1 lambdaFactory$(SearchProgressFragment searchProgressFragment) {
        return new SearchProgressFragment$$Lambda$1(searchProgressFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onViewCreated$0((SearchData) obj);
    }
}
