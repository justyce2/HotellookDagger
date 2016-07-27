package com.hotellook.ui.screen.gallery;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DrawableAnimator$$Lambda$1 implements AnimatorUpdateListener {
    private final DrawableAnimator arg$1;

    private DrawableAnimator$$Lambda$1(DrawableAnimator drawableAnimator) {
        this.arg$1 = drawableAnimator;
    }

    public static AnimatorUpdateListener lambdaFactory$(DrawableAnimator drawableAnimator) {
        return new DrawableAnimator$$Lambda$1(drawableAnimator);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$new$0(valueAnimator);
    }
}
