package com.hotellook.ui.screen.searchform;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchFormFragment$$Lambda$4 implements Action1 {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$4(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static Action1 lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$4(searchFormFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onLocationUpdate((Location) obj);
    }
}
