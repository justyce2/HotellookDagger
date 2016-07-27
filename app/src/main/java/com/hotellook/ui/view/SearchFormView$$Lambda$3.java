package com.hotellook.ui.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormView$$Lambda$3 implements AnimatorUpdateListener {
    private final SearchFormView arg$1;

    private SearchFormView$$Lambda$3(SearchFormView searchFormView) {
        this.arg$1 = searchFormView;
    }

    public static AnimatorUpdateListener lambdaFactory$(SearchFormView searchFormView) {
        return new SearchFormView$$Lambda$3(searchFormView);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$getResizeAnimator$2(valueAnimator);
    }
}
