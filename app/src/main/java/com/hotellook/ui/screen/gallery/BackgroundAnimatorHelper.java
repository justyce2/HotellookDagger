package com.hotellook.ui.screen.gallery;

import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.hotellook.utils.evaluator.ArgbEvaluator;

public class BackgroundAnimatorHelper {
    @NonNull
    public static ValueAnimator createBackgroundInAnimator(View view) {
        return createAnimator(view, ContextCompat.getColor(view.getContext(), 17170444));
    }

    @NonNull
    public static ValueAnimator createBackgroundOutAnimator(View view) {
        return createAnimator(view, ContextCompat.getColor(view.getContext(), 17170445));
    }

    @NonNull
    private static ValueAnimator createAnimator(View view, int targetColor) {
        ValueAnimator alphaAnimator = ValueAnimator.ofInt(new int[]{((ColorDrawable) view.getBackground()).getColor(), targetColor});
        alphaAnimator.setEvaluator(ArgbEvaluator.getInstance());
        alphaAnimator.addUpdateListener(BackgroundAnimatorHelper$$Lambda$1.lambdaFactory$(view));
        return alphaAnimator;
    }

    public static void animateToTargetColor(View view) {
        ValueAnimator animator = createBackgroundInAnimator(view);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    public static void setTransitionColor(View view, float fraction) {
        view.setBackgroundColor(((Integer) new ArgbEvaluator().evaluate(fraction, Integer.valueOf(ContextCompat.getColor(view.getContext(), 17170445)), Integer.valueOf(ContextCompat.getColor(view.getContext(), 17170444)))).intValue());
    }
}
