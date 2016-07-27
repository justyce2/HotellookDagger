package com.hotellook.ui.screen.hotel.general;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BestOfferView$$Lambda$4 implements AnimatorUpdateListener {
    private final BestOfferView arg$1;

    private BestOfferView$$Lambda$4(BestOfferView bestOfferView) {
        this.arg$1 = bestOfferView;
    }

    public static AnimatorUpdateListener lambdaFactory$(BestOfferView bestOfferView) {
        return new BestOfferView$$Lambda$4(bestOfferView);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$createResizeAnimator$2(valueAnimator);
    }
}
