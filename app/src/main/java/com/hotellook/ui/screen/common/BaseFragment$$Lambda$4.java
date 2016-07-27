package com.hotellook.ui.screen.common;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.MenuItem;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BaseFragment$$Lambda$4 implements AnimatorUpdateListener {
    private final MenuItem[] arg$1;

    private BaseFragment$$Lambda$4(MenuItem[] menuItemArr) {
        this.arg$1 = menuItemArr;
    }

    public static AnimatorUpdateListener lambdaFactory$(MenuItem[] menuItemArr) {
        return new BaseFragment$$Lambda$4(menuItemArr);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        BaseFragment.lambda$null$2(this.arg$1, valueAnimator);
    }
}
