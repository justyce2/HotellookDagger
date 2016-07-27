package com.hotellook.ui.view.viewmovers;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import java.util.ArrayList;
import java.util.List;

public class SyncScrollerDelegate implements OnTouchListener, ScrollListener {
    private static final long ANIM_DELAY = 300;
    private static final long ANIM_DURATION = 250;
    public static final int BACK_ANIMATION_DELAY_MILLIS = 2000;
    private AnimatorSet finishAnimatorSet;
    private final Handler handler;
    private final ScrollableWrapper scrollable;
    private final List<ViewTranslationData> translateViews;

    public SyncScrollerDelegate(ScrollableWrapper scrollable) {
        this.translateViews = new ArrayList();
        this.handler = new Handler();
        this.finishAnimatorSet = new AnimatorSet();
        this.scrollable = scrollable;
        scrollable.listenScrolls(this);
        scrollable.listenTouches(this);
    }

    public void onScroll(float dy) {
        this.finishAnimatorSet.cancel();
        for (ViewTranslationData view : this.translateViews) {
            translate(dy, view);
        }
    }

    public void onScrollFinished() {
        animateDelayed();
    }

    public void cancelAllAnimations() {
        this.handler.removeCallbacksAndMessages(null);
        this.finishAnimatorSet.cancel();
    }

    public boolean onTouch(View v, MotionEvent event) {
        animateDelayed();
        if (isTouchFinished(event)) {
            onTouchFinished();
        }
        return false;
    }

    private void animateDelayed() {
        this.handler.removeCallbacksAndMessages(null);
        this.handler.postDelayed(SyncScrollerDelegate$$Lambda$1.lambdaFactory$(this), 2000);
    }

    /* synthetic */ void lambda$animateDelayed$0() {
        for (ViewTranslationData view : this.translateViews) {
            if (isRunning(this.finishAnimatorSet)) {
                this.finishAnimatorSet.cancel();
            }
            animateBack(view);
            this.finishAnimatorSet.setDuration(ANIM_DURATION);
            this.finishAnimatorSet.start();
        }
    }

    private void animateBack(ViewTranslationData viewData) {
        List<Animator> animatorList = new ArrayList();
        animatorList.add(ObjectAnimator.ofFloat(viewData.translateView, View.TRANSLATION_Y, new float[]{0.0f}));
        if (viewData.hideView != null) {
            animatorList.add(ObjectAnimator.ofFloat(viewData.hideView, View.ALPHA, new float[]{1.0f}));
        }
        this.finishAnimatorSet.playTogether(animatorList);
    }

    private boolean isRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    private void onTouchFinished() {
        this.finishAnimatorSet.cancel();
        this.finishAnimatorSet = new AnimatorSet();
        for (ViewTranslationData viewTranslationData : this.translateViews) {
            addAnimationsToSet(viewTranslationData);
        }
        this.finishAnimatorSet.setDuration(ANIM_DURATION);
        this.finishAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        if (!this.translateViews.isEmpty()) {
            ((ViewTranslationData) this.translateViews.get(0)).translateView.postDelayed(SyncScrollerDelegate$$Lambda$2.lambdaFactory$(this), ANIM_DELAY);
        }
    }

    /* synthetic */ void lambda$onTouchFinished$1() {
        this.finishAnimatorSet.start();
    }

    public void addViewToTranslate(View view, int translation) {
        this.translateViews.add(new ViewTranslationData(view, translation));
    }

    public void addViewToTranslateAndHide(View view, View hideView, int translation) {
        this.translateViews.add(new ViewTranslationData(view, hideView, translation));
    }

    public void addViewToTranslateAndHide(View view, int translation) {
        this.translateViews.add(new ViewTranslationData(view, view, translation));
    }

    private int limitTranslation(ViewTranslationData view, float translationSignum, int newTranslation) {
        if (((float) newTranslation) * translationSignum < 0.0f) {
            newTranslation = 0;
        }
        if (translationSignum > 0.0f) {
            if (newTranslation > view.maxTranslation) {
                return view.maxTranslation;
            }
            return newTranslation;
        } else if (newTranslation < view.maxTranslation) {
            return view.maxTranslation;
        } else {
            return newTranslation;
        }
    }

    public float convertTranslationIntoAlpha(float translation, float maxTranslation) {
        return 1.0f - Math.abs(translation / maxTranslation);
    }

    private void addAnimationsToSet(ViewTranslationData viewTranslationData) {
        int newTranslation;
        this.finishAnimatorSet.cancel();
        List<Animator> animatorList = new ArrayList();
        if (shouldScrollBackToStartPosition(viewTranslationData, -this.scrollable.scrollY(), (int) viewTranslationData.translateView.getTranslationY())) {
            newTranslation = 0;
        } else {
            newTranslation = viewTranslationData.maxTranslation;
        }
        animatorList.add(ObjectAnimator.ofFloat(viewTranslationData.translateView, View.TRANSLATION_Y, new float[]{(float) newTranslation}));
        if (viewTranslationData.hideView != null) {
            animatorList.add(ObjectAnimator.ofFloat(viewTranslationData.hideView, View.ALPHA, new float[]{convertTranslationIntoAlpha((float) newTranslation, (float) viewTranslationData.maxTranslation)}));
        }
        this.finishAnimatorSet.playTogether(animatorList);
    }

    private boolean shouldScrollBackToStartPosition(ViewTranslationData viewTranslationData, int scrollY, int currentTranslation) {
        return Math.abs(scrollY) < Math.abs(viewTranslationData.maxTranslation) || Math.abs(currentTranslation) < Math.abs(viewTranslationData.maxTranslation / 2);
    }

    private boolean isTouchFinished(MotionEvent event) {
        int action = event.getAction();
        if (action == 1 || action == 3) {
            return true;
        }
        return false;
    }

    private void applyAlpha(ViewTranslationData viewTranslationData, int translation) {
        if (viewTranslationData.hideView != null) {
            viewTranslationData.hideView.setAlpha(convertTranslationIntoAlpha((float) translation, (float) viewTranslationData.maxTranslation));
        }
    }

    private void translate(float dy, ViewTranslationData viewData) {
        if (!this.finishAnimatorSet.isRunning() && !this.finishAnimatorSet.isStarted()) {
            float translationSignum = Math.signum((float) viewData.maxTranslation);
            int newTranslation = limitTranslation(viewData, translationSignum, (int) (viewData.translateView.getTranslationY() - (translationSignum * dy)));
            viewData.translateView.setTranslationY((float) newTranslation);
            applyAlpha(viewData, newTranslation);
        }
    }

    public void moveBack() {
        onTouchFinished();
    }
}
