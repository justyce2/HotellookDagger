package com.hotellook.ui.screen.hotel;

import android.view.animation.Interpolator;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ScrollableFrameLayout$$Lambda$1 implements Interpolator {
    private static final ScrollableFrameLayout$$Lambda$1 instance;

    static {
        instance = new ScrollableFrameLayout$$Lambda$1();
    }

    private ScrollableFrameLayout$$Lambda$1() {
    }

    public static Interpolator lambdaFactory$() {
        return instance;
    }

    @Hidden
    public float getInterpolation(float f) {
        return t -= 1.0f;
    }
}
