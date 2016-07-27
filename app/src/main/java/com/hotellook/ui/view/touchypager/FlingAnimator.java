package com.hotellook.ui.view.touchypager;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import timber.log.Timber;

public class FlingAnimator extends ValueAnimator implements AnimatorUpdateListener {
    private final FlingListener mFlingListener;
    private final Scroller mScroller;
    private int mStartX;

    public interface FlingListener {
        void onFlingEnd();

        void onFlingUpdate(int i, int i2);
    }

    public FlingAnimator(Context context, FlingListener flingListener) {
        this.mFlingListener = flingListener;
        this.mScroller = new Scroller(context, new AccelerateDecelerateInterpolator());
        setInterpolator(new LinearInterpolator());
        setFloatValues(new float[]{0.0f, 1.0f});
        addUpdateListener(this);
    }

    public void prepare(int x, int y, int velocityX, int velocityY, ImageFlingLimits imageFlingLimits) {
        Timber.m751d("On fling started x: %d y: %d vX: %d vY: %d %s", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(velocityX), Integer.valueOf(velocityY), imageFlingLimits);
        stopAnimation();
        this.mScroller.fling(x, y, velocityX, velocityY, imageFlingLimits.minX, imageFlingLimits.maxX, imageFlingLimits.minY, imageFlingLimits.maxY);
        setDuration(10000);
        this.mStartX = x;
    }

    private void stopAnimation() {
        cancel();
        this.mScroller.abortAnimation();
    }

    public void onAnimationUpdate(ValueAnimator animation) {
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingListener.onFlingUpdate(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            return;
        }
        Timber.m751d("On fling finished", new Object[0]);
        cancel();
        this.mFlingListener.onFlingEnd();
    }
}
