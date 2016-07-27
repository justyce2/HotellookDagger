package com.hotellook.ui.view.viewmovers;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.util.ArrayList;
import java.util.List;

public class AsyncScrollerDelegate implements OnTouchListener, ScrollListener {
    private static final long ANIM_DURATION = 250;
    public static final int BACK_ANIMATION_DELAY_MILLIS = 2000;
    private final Handler handler;
    private final List<AsyncViewTranslationData> translateViews;

    private static class AsyncViewTranslationData extends ViewTranslationData {
        public AnimatorSet animator;
        public AnimatorSet backAnimator;
        public int delta;

        AsyncViewTranslationData(View translateView, int maxTranslation) {
            super(translateView, maxTranslation);
        }

        AsyncViewTranslationData(View translateView, View hideView, int maxTranslation) {
            super(translateView, hideView, maxTranslation);
        }
    }

    public AsyncScrollerDelegate(ScrollableWrapper scrollable) {
        this.translateViews = new ArrayList();
        this.handler = new Handler();
        scrollable.listenTouches(this);
        scrollable.listenScrolls(this);
    }

    public void addViewToTranslateAsync(View view, View hideView, int translation) {
        this.translateViews.add(new AsyncViewTranslationData(view, hideView, translation));
    }

    public void onScroll(float dy) {
        for (AsyncViewTranslationData view : this.translateViews) {
            scrolled(dy, view);
        }
    }

    public void onScrollFinished() {
        animateDelayed();
    }

    public boolean onTouch(View v, MotionEvent event) {
        animateDelayed();
        return false;
    }

    private void animateDelayed() {
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(AsyncScrollerDelegate$$Lambda$1.lambdaFactory$(this), 2000);
    }

    /* synthetic */ void lambda$animateDelayed$0() {
        for (AsyncViewTranslationData view : this.translateViews) {
            animateBack(view);
        }
    }

    private void scrolled(float dy, AsyncViewTranslationData viewData) {
        viewData.delta = (int) (((float) viewData.delta) + dy);
        if (overscrolled(viewData)) {
            if (viewData.delta < 0) {
                animateToTargetTranslate(viewData);
            } else {
                animateBack(viewData);
            }
            viewData.delta = 0;
        }
    }

    private boolean overscrolled(AsyncViewTranslationData viewData) {
        return Math.abs(viewData.delta) > Math.abs(viewData.maxTranslation);
    }

    private boolean sameDirection(float dy, AsyncViewTranslationData viewData) {
        return ((float) viewData.maxTranslation) * dy > 0.0f;
    }

    private void animateToTargetTranslate(AsyncViewTranslationData viewData) {
        if (!isRunning(viewData.animator) && !isRunning(viewData.backAnimator)) {
            viewData.animator = new AnimatorSet();
            List<Animator> animatorList = new ArrayList();
            animatorList.add(ObjectAnimator.ofFloat(viewData.translateView, View.TRANSLATION_Y, new float[]{(float) viewData.maxTranslation}));
            if (viewData.hideView != null) {
                animatorList.add(ObjectAnimator.ofFloat(viewData.hideView, View.ALPHA, new float[]{0.0f}));
            }
            viewData.animator.playTogether(animatorList);
            viewData.animator.setDuration(ANIM_DURATION);
            viewData.animator.start();
        }
    }

    private boolean isRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    private void animateBack(AsyncViewTranslationData viewData) {
        if (!isRunning(viewData.backAnimator)) {
            cancelCurrentAnimator(viewData);
            viewData.backAnimator = new AnimatorSet();
            List<Animator> animatorList = new ArrayList();
            animatorList.add(ObjectAnimator.ofFloat(viewData.translateView, View.TRANSLATION_Y, new float[]{0.0f}));
            if (viewData.hideView != null) {
                animatorList.add(ObjectAnimator.ofFloat(viewData.hideView, View.ALPHA, new float[]{1.0f}));
            }
            viewData.backAnimator.playTogether(animatorList);
            viewData.backAnimator.setDuration(ANIM_DURATION);
            viewData.backAnimator.start();
        }
    }

    private void cancelCurrentAnimator(AsyncViewTranslationData viewData) {
        Animator animator = viewData.animator;
        if (animator != null) {
            animator.cancel();
        }
    }

    public void moveBack() {
        for (AsyncViewTranslationData view : this.translateViews) {
            animateBack(view);
        }
    }

    public void cancelAllAnimatiors() {
        this.handler.removeCallbacksAndMessages(null);
        for (AsyncViewTranslationData view : this.translateViews) {
            if (isRunning(view.animator)) {
                view.animator.cancel();
            }
            if (isRunning(view.backAnimator)) {
                view.backAnimator.cancel();
            }
        }
    }
}
