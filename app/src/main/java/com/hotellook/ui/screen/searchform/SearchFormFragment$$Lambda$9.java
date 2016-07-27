package com.hotellook.ui.screen.searchform;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$9 implements Runnable {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$9(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static Runnable lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$9(searchFormFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$addCityPhotoToImage$6();
    }
}
