package com.hotellook.ui.screen.gallery;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BackgroundAnimatorHelper$$Lambda$1 implements AnimatorUpdateListener {
    private final View arg$1;

    private BackgroundAnimatorHelper$$Lambda$1(View view) {
        this.arg$1 = view;
    }

    public static AnimatorUpdateListener lambdaFactory$(View view) {
        return new BackgroundAnimatorHelper$$Lambda$1(view);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.setBackgroundColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }
}
