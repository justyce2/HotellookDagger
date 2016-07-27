package com.hotellook.ui.screen.searchform;

import com.google.android.gms.maps.GoogleMap;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$10 implements Runnable {
    private final SearchFormFragment arg$1;
    private final GoogleMap arg$2;

    private SearchFormFragment$$Lambda$10(SearchFormFragment searchFormFragment, GoogleMap googleMap) {
        this.arg$1 = searchFormFragment;
        this.arg$2 = googleMap;
    }

    public static Runnable lambdaFactory$(SearchFormFragment searchFormFragment, GoogleMap googleMap) {
        return new SearchFormFragment$$Lambda$10(searchFormFragment, googleMap);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$setUpCoordinatesSearch$7(this.arg$2);
    }
}
