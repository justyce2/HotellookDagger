package com.hotellook.ui.screen.searchform;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormMarkerAnimator$$Lambda$2 implements AnimatorUpdateListener {
    private final SearchFormMarkerAnimator arg$1;

    private SearchFormMarkerAnimator$$Lambda$2(SearchFormMarkerAnimator searchFormMarkerAnimator) {
        this.arg$1 = searchFormMarkerAnimator;
    }

    public static AnimatorUpdateListener lambdaFactory$(SearchFormMarkerAnimator searchFormMarkerAnimator) {
        return new SearchFormMarkerAnimator$$Lambda$2(searchFormMarkerAnimator);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$animatePinBounce$1(valueAnimator);
    }
}
