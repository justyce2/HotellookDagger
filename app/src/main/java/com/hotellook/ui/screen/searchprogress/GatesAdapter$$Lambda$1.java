package com.hotellook.ui.screen.searchprogress;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatesAdapter$$Lambda$1 implements AnimatorUpdateListener {
    private final GateViewHolder arg$1;

    private GatesAdapter$$Lambda$1(GateViewHolder gateViewHolder) {
        this.arg$1 = gateViewHolder;
    }

    public static AnimatorUpdateListener lambdaFactory$(GateViewHolder gateViewHolder) {
        return new GatesAdapter$$Lambda$1(gateViewHolder);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        GatesAdapter.lambda$animateToLoaded$0(this.arg$1, valueAnimator);
    }
}
