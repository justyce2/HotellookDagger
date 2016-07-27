package com.hotellook.ui.screen.searchform;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchFormFragment$$Lambda$2 implements Action1 {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$2(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static Action1 lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$2(searchFormFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeCoordinatesSearchRequest$1((Boolean) obj);
    }
}
