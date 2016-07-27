package com.hotellook.ui.screen.searchprogress;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchProgressFragment$$Lambda$8 implements AnimatorUpdateListener {
    private final SearchProgressFragment arg$1;

    private SearchProgressFragment$$Lambda$8(SearchProgressFragment searchProgressFragment) {
        this.arg$1 = searchProgressFragment;
    }

    public static AnimatorUpdateListener lambdaFactory$(SearchProgressFragment searchProgressFragment) {
        return new SearchProgressFragment$$Lambda$8(searchProgressFragment);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$animateProgressToMaxAndShowResults$7(valueAnimator);
    }
}
