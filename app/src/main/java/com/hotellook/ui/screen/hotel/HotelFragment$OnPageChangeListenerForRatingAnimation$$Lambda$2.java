package com.hotellook.ui.screen.hotel;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$2 implements Runnable {
    private final OnPageChangeListenerForRatingAnimation arg$1;
    private final OnPageChangeListener arg$2;

    private HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$2(OnPageChangeListenerForRatingAnimation onPageChangeListenerForRatingAnimation, OnPageChangeListener onPageChangeListener) {
        this.arg$1 = onPageChangeListenerForRatingAnimation;
        this.arg$2 = onPageChangeListener;
    }

    public static Runnable lambdaFactory$(OnPageChangeListenerForRatingAnimation onPageChangeListenerForRatingAnimation, OnPageChangeListener onPageChangeListener) {
        return new HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$2(onPageChangeListenerForRatingAnimation, onPageChangeListener);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onPageSelected$1(this.arg$2);
    }
}
