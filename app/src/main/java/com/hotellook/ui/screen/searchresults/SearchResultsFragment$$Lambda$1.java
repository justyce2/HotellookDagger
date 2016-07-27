package com.hotellook.ui.screen.searchresults;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchResultsFragment$$Lambda$1 implements Runnable {
    private final SearchResultsFragment arg$1;

    private SearchResultsFragment$$Lambda$1(SearchResultsFragment searchResultsFragment) {
        this.arg$1 = searchResultsFragment;
    }

    public static Runnable lambdaFactory$(SearchResultsFragment searchResultsFragment) {
        return new SearchResultsFragment$$Lambda$1(searchResultsFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$restoreFromSnapshot$0();
    }
}
