package com.hotellook.ui.screen.searchform;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$7 implements Runnable {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$7(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static Runnable lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$7(searchFormFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$animateMapAndPinAppearing$4();
    }
}
