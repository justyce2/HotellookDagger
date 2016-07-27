package com.hotellook.ui.screen.gallery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DrawableAnimator extends ValueAnimator {
    private static final int ALPHA_FULL = 255;
    private final Drawable drawable;
    private boolean isAnimating;
    private final AnimationEndListener listener;

    /* renamed from: com.hotellook.ui.screen.gallery.DrawableAnimator.1 */
    class C12891 extends AnimatorListenerAdapter {
        C12891() {
        }

        public void onAnimationEnd(Animator animation) {
            DrawableAnimator.this.isAnimating = false;
            if (DrawableAnimator.this.listener != null) {
                DrawableAnimator.this.listener.onAnimationEnd();
            }
        }
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    public DrawableAnimator(@NonNull Drawable drawable, @Nullable AnimationEndListener listener) {
        this.isAnimating = false;
        this.drawable = drawable;
        this.listener = listener;
        addUpdateListener(DrawableAnimator$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$new$0(ValueAnimator animation) {
        this.drawable.mutate().setAlpha(((Integer) animation.getAnimatedValue()).intValue());
    }

    public static DrawableAnimator showAnimator(@NonNull Drawable drawable, @Nullable AnimationEndListener listener) {
        DrawableAnimator animator = new DrawableAnimator(drawable, listener);
        animator.prepareValues(0, ALPHA_FULL);
        return animator;
    }

    public static DrawableAnimator hideAnimator(@NonNull Drawable drawable, @Nullable AnimationEndListener listener) {
        DrawableAnimator animator = new DrawableAnimator(drawable, listener);
        animator.prepareValues(ALPHA_FULL, 0);
        return animator;
    }

    public static Animator crossAnimator(@NonNull Drawable from, @NonNull Drawable to, long duration, @Nullable AnimationEndListener listener) {
        hideAnimator(from, listener).setDuration(duration / 2);
        showAnimator(to, null).setDuration(duration / 2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(new Animator[]{hideAnimator, showAnimator});
        return animatorSet;
    }

    public void hide() {
        prepareValues(ALPHA_FULL, 0);
        start();
    }

    public void show() {
        prepareValues(0, ALPHA_FULL);
        start();
    }

    public void showInstantly() {
        this.drawable.mutate().setAlpha(ALPHA_FULL);
    }

    public void hideInstantly() {
        this.drawable.mutate().setAlpha(0);
    }

    public void prepareValues(int from, int to) {
        cancel();
        setIntValues(new int[]{from, to});
        addListener(new C12891());
        this.isAnimating = true;
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }
}
