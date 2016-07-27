package com.hotellook.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.support.annotation.NonNull;
import android.view.View;

public class Animators {

    /* renamed from: com.hotellook.utils.Animators.1 */
    static class C14541 implements AnimatorUpdateListener {
        final /* synthetic */ View val$fadeInView;
        final /* synthetic */ View val$fadeOutView;

        C14541(View view, View view2) {
            this.val$fadeInView = view;
            this.val$fadeOutView = view2;
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            this.val$fadeInView.setAlpha(animation.getAnimatedFraction());
            this.val$fadeOutView.setAlpha(1.0f - animation.getAnimatedFraction());
        }
    }

    /* renamed from: com.hotellook.utils.Animators.2 */
    static class C14552 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$fadeInView;
        final /* synthetic */ View val$fadeOutView;
        final /* synthetic */ int val$hiddenState;

        C14552(View view, View view2, int i) {
            this.val$fadeInView = view;
            this.val$fadeOutView = view2;
            this.val$hiddenState = i;
        }

        public void onAnimationStart(Animator animation) {
            this.val$fadeInView.setAlpha(0.0f);
            this.val$fadeInView.setVisibility(0);
        }

        public void onAnimationEnd(Animator animation) {
            this.val$fadeOutView.setVisibility(this.val$hiddenState);
        }
    }

    public static Animator createCrossFadeAnimator(@NonNull View fadeInView, @NonNull View fadeOutView, int hiddenState) {
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new C14541(fadeInView, fadeOutView));
        animator.addListener(new C14552(fadeInView, fadeOutView, hiddenState));
        return animator;
    }
}
