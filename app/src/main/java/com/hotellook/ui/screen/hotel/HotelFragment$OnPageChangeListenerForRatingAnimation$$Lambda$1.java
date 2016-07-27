package com.hotellook.ui.screen.hotel;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$1 implements Runnable {
    private final OnPageChangeListenerForRatingAnimation arg$1;
    private final OnPageChangeListener arg$2;

    private HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$1(OnPageChangeListenerForRatingAnimation onPageChangeListenerForRatingAnimation, OnPageChangeListener onPageChangeListener) {
        this.arg$1 = onPageChangeListenerForRatingAnimation;
        this.arg$2 = onPageChangeListener;
    }

    public static Runnable lambdaFactory$(OnPageChangeListenerForRatingAnimation onPageChangeListenerForRatingAnimation, OnPageChangeListener onPageChangeListener) {
        return new HotelFragment$OnPageChangeListenerForRatingAnimation$$Lambda$1(onPageChangeListenerForRatingAnimation, onPageChangeListener);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onPageScrolled$0(this.arg$2);
    }
}
