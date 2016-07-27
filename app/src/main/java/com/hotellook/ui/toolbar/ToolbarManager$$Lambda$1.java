package com.hotellook.ui.toolbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToolbarManager$$Lambda$1 implements AnimatorUpdateListener {
    private final ToolbarManager arg$1;

    private ToolbarManager$$Lambda$1(ToolbarManager toolbarManager) {
        this.arg$1 = toolbarManager;
    }

    public static AnimatorUpdateListener lambdaFactory$(ToolbarManager toolbarManager) {
        return new ToolbarManager$$Lambda$1(toolbarManager);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$addStatusBarColorAnimator$0(valueAnimator);
    }
}
