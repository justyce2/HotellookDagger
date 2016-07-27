package com.hotellook.ui.screen.searchform;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchFormFragment$$Lambda$5 implements Action1 {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$5(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static Action1 lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$5(searchFormFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.handleLocationErrors((Throwable) obj);
    }
}
