package com.hotellook.ui.view.touchypager;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.hotellook.ui.view.touchypager.FlingAnimator.FlingListener;
import com.hotellook.utils.AndroidUtils;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class ImageFormController implements OnDoubleTapListener, OnGestureListener, OnScaleGestureListener, FlingListener {
    private static final int ANIMATION_DURATION = 300;
    private static final float MAX_SCALE_LANDSCAPE_CONST = 2.0f;
    private static final float MAX_SCALE_PORTRAIT_CONST = 3.0f;
    public static final int MIN_FLING = 50;
    public static final int MIN_Y_TRANSLATE = 10;
    private float lastFocusX;
    private float lastFocusY;
    private final Context mContext;
    private boolean mFirstInPager;
    private GestureDetector mGestureDetector;
    private final RectF mImageRect;
    private boolean mIsEnabled;
    private boolean mIsScrolling;
    private boolean mLastInPager;
    private float mLastScaleFocusX;
    private float mLastScaleFocusY;
    private final Matrix mMatrix;
    private float mMaxScale;
    private float mMinScale;
    private boolean mPagerMode;
    private Animator mPositionAndScaleAnimator;
    private float mScale;
    private ScaleGestureDetector mScaleDetector;
    private float[] mTempValues;
    private TouchListener mTouchListener;
    private float mTranslateX;
    private float mTranstaleY;
    private VerticalScrollListener mVerticalScrollListener;
    private final View mView;
    private final RectF mViewRect;

    private class ScaleAnimator extends ValueAnimator {
        private final float mStartScale;
        private final float mTargetScale;
        private final float mTargetX;
        private final float mTargetY;

        /* renamed from: com.hotellook.ui.view.touchypager.ImageFormController.ScaleAnimator.1 */
        class C14491 implements AnimatorUpdateListener {
            final /* synthetic */ ImageFormController val$this$0;

            C14491(ImageFormController imageFormController) {
                this.val$this$0 = imageFormController;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                float prevScale = ImageFormController.this.mScale;
                ImageFormController.this.mScale = (((Float) animation.getAnimatedValue()).floatValue() * (ScaleAnimator.this.mTargetScale - ScaleAnimator.this.mStartScale)) + ScaleAnimator.this.mStartScale;
                float scaleFactor = ImageFormController.this.mScale / prevScale;
                ImageFormController.this.mMatrix.postTranslate(-ScaleAnimator.this.mTargetX, -ScaleAnimator.this.mTargetY);
                ImageFormController.this.mMatrix.postScale(scaleFactor, scaleFactor);
                ImageFormController.this.mMatrix.postTranslate(ScaleAnimator.this.mTargetX, ScaleAnimator.this.mTargetY);
                ImageFormController.this.limitTranslations();
                ImageFormController.this.mTouchListener.onAnimationUpdate();
            }
        }

        public ScaleAnimator(float targetScale, float focusX, float focusY) {
            this.mTargetScale = targetScale;
            this.mTargetX = focusX;
            this.mTargetY = focusY;
            this.mStartScale = ImageFormController.this.mScale;
            setFloatValues(new float[]{0.0f, 1.0f});
            addUpdateListener(new C14491(ImageFormController.this));
        }
    }

    private class TranslateAnimator extends ValueAnimator {
        private final float mFullDeltaX;
        private final float mFullDeltaY;
        private final float mStartX;
        private final float mStartY;

        /* renamed from: com.hotellook.ui.view.touchypager.ImageFormController.TranslateAnimator.1 */
        class C14501 implements AnimatorUpdateListener {
            final /* synthetic */ ImageFormController val$this$0;

            C14501(ImageFormController imageFormController) {
                this.val$this$0 = imageFormController;
            }

            public void onAnimationUpdate(ValueAnimator animation) {
                ImageFormController.this.updateTranslates();
                float fraction = ((Float) animation.getAnimatedValue()).floatValue();
                ImageFormController.this.mMatrix.postTranslate(TranslateAnimator.this.getDx(TranslateAnimator.this.mStartX + (TranslateAnimator.this.mFullDeltaX * fraction)), TranslateAnimator.this.getDy(TranslateAnimator.this.mStartY + (TranslateAnimator.this.mFullDeltaY * fraction)));
                ImageFormController.this.mTouchListener.onAnimationUpdate();
                ImageFormController.this.updateTranslates();
                Timber.m751d("Translate to current %f %f", Float.valueOf(ImageFormController.this.mTranslateX), Float.valueOf(ImageFormController.this.mTranstaleY));
            }
        }

        public TranslateAnimator(float targetTranslateX, float targetTranslateY) {
            ImageFormController.this.updateTranslates();
            this.mStartX = ImageFormController.this.mTranslateX;
            this.mStartY = ImageFormController.this.mTranstaleY;
            this.mFullDeltaX = targetTranslateX - this.mStartX;
            this.mFullDeltaY = targetTranslateY - this.mStartY;
            Timber.m751d("Translate to %f %f", Float.valueOf(targetTranslateX), Float.valueOf(targetTranslateY));
            setFloatValues(new float[]{0.0f, 1.0f});
            addUpdateListener(new C14501(ImageFormController.this));
        }

        private float getDy(float newTranslationY) {
            return newTranslationY - ImageFormController.this.mTranstaleY;
        }

        private float getDx(float newTranslateX) {
            return newTranslateX - ImageFormController.this.mTranslateX;
        }
    }

    public ImageFormController(Context context, View view) {
        float f = MAX_SCALE_PORTRAIT_CONST;
        this.mViewRect = new RectF();
        this.mImageRect = new RectF();
        this.mMatrix = new Matrix();
        this.mIsEnabled = false;
        this.mTouchListener = new NullTouchListener();
        this.mVerticalScrollListener = new NullVerticalScrollListener();
        this.mMinScale = 0.7f;
        this.mMaxScale = MAX_SCALE_PORTRAIT_CONST;
        this.mScale = 1.0f;
        this.mIsScrolling = false;
        this.mPagerMode = false;
        this.mFirstInPager = false;
        this.mLastInPager = false;
        this.mTempValues = new float[9];
        this.mContext = context;
        this.mView = view;
        this.mScaleDetector = new ScaleGestureDetector(context, this);
        this.mGestureDetector = new SafeGestureDetector(context, this);
        this.mGestureDetector.setOnDoubleTapListener(this);
        this.mGestureDetector.setIsLongpressEnabled(false);
        if (!AndroidUtils.isPortrait(context)) {
            f = MAX_SCALE_LANDSCAPE_CONST;
        }
        this.mMaxScale = f;
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mIsEnabled = enabled;
    }

    public void setListener(TouchListener touchListener) {
        this.mTouchListener = touchListener;
        if (this.mTouchListener == null) {
            this.mTouchListener = new NullTouchListener();
        }
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public void reset() {
        this.mMatrix.reset();
        this.mScale = 1.0f;
        this.mTouchListener.onTransformChanged();
    }

    public void setPagerMode(boolean pagerMode) {
        this.mPagerMode = pagerMode;
    }

    public void setVerticalScrollListener(VerticalScrollListener verticalScrollListener) {
        this.mVerticalScrollListener = verticalScrollListener;
        if (this.mVerticalScrollListener == null) {
            this.mVerticalScrollListener = new NullVerticalScrollListener();
        }
    }

    public float getCenterTranslationX() {
        return getCurrentImageRect().centerX() - this.mViewRect.centerX();
    }

    public float getCenterTranslationY() {
        return getCurrentImageRect().centerY() - this.mViewRect.centerY();
    }

    public void setFirstInPager(boolean firstInPager) {
        this.mFirstInPager = firstInPager;
    }

    public void setLastInPager(boolean lastInPager) {
        this.mLastInPager = lastInPager;
    }

    public void updateBounds(RectF viewBounds, RectF imageBounds) {
        this.mImageRect.set(imageBounds);
        this.mViewRect.set(viewBounds);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & 255;
        if (!CheckHelper.isTouchFinished(action)) {
            cancelCurrentAnimator();
        }
        Timber.m751d("Event on image %s", event);
        if (!this.mIsEnabled) {
            return false;
        }
        boolean handled;
        if (this.mScaleDetector.onTouchEvent(event) && CheckHelper.isScale(event)) {
            handled = true;
        } else {
            handled = false;
        }
        updateTranslates();
        boolean handledByScrollListener = false;
        if (CheckHelper.isTouchFinished(action) && this.mIsScrolling && (this.mPositionAndScaleAnimator == null || !this.mPositionAndScaleAnimator.isRunning())) {
            animateToCorrectPosition();
            if (this.mScale == 1.0f) {
                handledByScrollListener = this.mVerticalScrollListener.onFinishScroll(this.mView, this.mTranstaleY);
                Timber.m751d("Animate out!!", new Object[0]);
            }
        }
        if (handledByScrollListener) {
            return true;
        }
        if (this.mGestureDetector.onTouchEvent(event) || handled) {
            handled = true;
        } else {
            handled = false;
        }
        if (!CheckHelper.isTouchFinished(action)) {
            return handled;
        }
        this.mIsScrolling = false;
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if ((event.getAction() & 255) == 2) {
            RectF currentImageRect = getCurrentImageRect();
            float distanceX = this.lastFocusX - event.getX();
            float distanceY = this.lastFocusY - event.getY();
            this.lastFocusX = event.getX();
            this.lastFocusY = event.getY();
            if (CheckHelper.isHorizontalScroll(distanceX, distanceY) && !CheckHelper.isScale(event) && (this.mTranstaleY == 0.0f || this.mScale != 1.0f)) {
                if (CheckHelper.isLeftScroll(distanceX) && CheckHelper.isRightSideReached(currentImageRect, this.mViewRect)) {
                    return this.mLastInPager;
                }
                if (CheckHelper.isRightScroll(distanceX) && CheckHelper.isLeftSideReached(currentImageRect, this.mViewRect)) {
                    return this.mFirstInPager;
                }
            }
        }
        this.lastFocusX = event.getX();
        this.lastFocusY = event.getY();
        return true;
    }

    @NonNull
    private RectF getCurrentImageRect() {
        RectF currentImageRect = new RectF(this.mImageRect);
        this.mMatrix.mapRect(currentImageRect);
        updateTranslates();
        return currentImageRect;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        Timber.m751d("On Scale", new Object[0]);
        float scaleFactor = detector.getScaleFactor();
        scaleFactor = alignScaleFactor(scaleFactor, this.mScale * scaleFactor);
        if (scaleFactor != 1.0f) {
            this.mScale *= scaleFactor;
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();
            this.mMatrix.postTranslate(-focusX, -focusY);
            this.mMatrix.postScale(scaleFactor, scaleFactor);
            this.mMatrix.postTranslate(focusX, focusY);
            this.mLastScaleFocusX = focusX;
            this.mLastScaleFocusY = focusY;
            this.mTouchListener.onTransformChanged();
        }
        return true;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Timber.m751d("On ScaleBegin", new Object[0]);
        cancelCurrentAnimator();
        this.lastFocusX = detector.getFocusX();
        this.lastFocusY = detector.getFocusY();
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        Timber.m751d("On Scale End", new Object[0]);
        this.lastFocusY = detector.getFocusY();
        this.lastFocusX = detector.getFocusX();
        animateToCorrectPosition();
    }

    public boolean onDown(MotionEvent e) {
        Timber.m751d("On Down", new Object[0]);
        return true;
    }

    public void onShowPress(MotionEvent e) {
        Timber.m751d("On press", new Object[0]);
        cancelCurrentAnimator();
    }

    public boolean onSingleTapUp(MotionEvent e) {
        Timber.m751d("On Single tap", new Object[0]);
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Timber.m751d("On Scroll dx %f dy %f", Float.valueOf(distanceX), Float.valueOf(distanceY));
        if (!this.mScaleDetector.isInProgress() && this.mScale <= this.mMaxScale) {
            if (this.mScale == 1.0f && !this.mIsScrolling) {
                this.mVerticalScrollListener.onStartScroll();
            }
            this.mIsScrolling = true;
            updateTranslates();
            if (this.mScale < 1.0f) {
                distanceX = 0.0f;
                distanceY = 0.0f;
            }
            RectF currentImageRect = getCurrentImageRect();
            if (this.mScale == 1.0f) {
                if (!this.mPagerMode) {
                    distanceX = VelocityUtils.applyTranslateVelocity(distanceX);
                    distanceY = VelocityUtils.applyTranslateVelocity(distanceY);
                } else if (this.mTranstaleY != 0.0f) {
                    distanceX = 0.0f;
                } else if (CheckHelper.isHorizontalScroll(distanceX, distanceY)) {
                    if ((this.mFirstInPager && (CheckHelper.isRightScroll(distanceX) || CheckHelper.isRightSideOverscrolled(currentImageRect, this.mViewRect))) || (this.mLastInPager && (CheckHelper.isLeftScroll(distanceX) || CheckHelper.isLeftSideOverscrolled(currentImageRect, this.mViewRect)))) {
                        distanceX = VelocityUtils.applyTranslateVelocity(distanceX);
                        distanceY = 0.0f;
                    }
                } else if (this.mTranslateX == 0.0f) {
                    distanceX = 0.0f;
                } else {
                    distanceY = 0.0f;
                }
            }
            if (this.mScale > 1.0f) {
                if (CheckHelper.isRightScroll(distanceX)) {
                    if (CheckHelper.isLeftSideReached(currentImageRect, this.mViewRect) && this.mFirstInPager) {
                        distanceX = VelocityUtils.applyTranslateVelocity(distanceX);
                    }
                    if (!this.mFirstInPager && currentImageRect.left - distanceX > this.mViewRect.left) {
                        distanceX = -Math.abs(this.mViewRect.left - currentImageRect.left);
                    }
                }
                if (CheckHelper.isLeftScroll(distanceX)) {
                    if (CheckHelper.isRightSideReached(currentImageRect, this.mViewRect) && this.mLastInPager) {
                        distanceX = VelocityUtils.applyTranslateVelocity(distanceX);
                    }
                    if (!this.mLastInPager && currentImageRect.right - distanceX < this.mViewRect.right) {
                        distanceX = Math.abs(this.mViewRect.right - currentImageRect.right);
                    }
                }
                if (CheckHelper.isUpScroll(distanceY) && CheckHelper.isBottomReached(currentImageRect, this.mViewRect)) {
                    distanceY = VelocityUtils.applyTranslateVelocity(distanceY);
                }
                if (CheckHelper.isDownScroll(distanceY) && CheckHelper.isTopReached(currentImageRect, this.mViewRect)) {
                    distanceY = VelocityUtils.applyTranslateVelocity(distanceY);
                }
            }
            this.mMatrix.postTranslate(-distanceX, -distanceY);
            this.mTouchListener.onTransformChanged();
            if (this.mScale == 1.0f && this.mVerticalScrollListener != null) {
                updateTranslates();
                this.mVerticalScrollListener.onScroll(this.mTranstaleY);
            }
        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
        Timber.m751d("On long press", new Object[0]);
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1 == null || e2 == null) {
            return false;
        }
        RectF currentImageRect = getCurrentImageRect();
        if (CheckHelper.imageLessThanView(this.mViewRect, currentImageRect)) {
            velocityY = 0.0f;
        }
        float dx = e1.getX() - e2.getX();
        float dy = e1.getY() - e2.getY();
        Timber.m751d("On fling", new Object[0]);
        if (Math.abs(dx) >= 50.0f || Math.abs(dy) >= 50.0f) {
            cancelCurrentAnimator();
            FlingAnimator animator = new FlingAnimator(this.mContext, this);
            animator.prepare((int) currentImageRect.centerX(), (int) currentImageRect.centerY(), (int) velocityX, (int) velocityY, new ImageFlingLimits(currentImageRect, this.mViewRect));
            animator.start();
            this.mPositionAndScaleAnimator = animator;
            return true;
        }
        Timber.m751d("Too small fling", new Object[0]);
        return false;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        Timber.m751d("On single tap confirmed", new Object[0]);
        return true;
    }

    public boolean onDoubleTap(MotionEvent e) {
        Timber.m751d("On double tap", new Object[0]);
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        Timber.m751d("On double tap event", new Object[0]);
        if (e.getAction() == 1) {
            if (this.mPositionAndScaleAnimator != null && this.mPositionAndScaleAnimator.isRunning()) {
                this.mPositionAndScaleAnimator.cancel();
            }
            animateDoubleTap(e.getRawX(), this.mViewRect.centerY());
        }
        return true;
    }

    private void cancelCurrentAnimator() {
        if (this.mPositionAndScaleAnimator != null && this.mPositionAndScaleAnimator.isRunning()) {
            this.mPositionAndScaleAnimator.cancel();
            this.mPositionAndScaleAnimator = null;
        }
    }

    private void animateToCorrectPosition() {
        startAnimation(prepareScaleAnimator(), prepareTranslateAnimator());
    }

    private void animateDoubleTap(float x, float y) {
        float targetScale = this.mScale == this.mMaxScale ? 1.0f : this.mMaxScale;
        startAnimation(new ScaleAnimator(targetScale, x, y), prepareTranslateAnimatorForDoubleTap(targetScale));
    }

    private void startAnimation(Animator... animators) {
        if (this.mPositionAndScaleAnimator == null || !this.mPositionAndScaleAnimator.isRunning()) {
            AnimatorSet set = new AnimatorSet();
            set.setInterpolator(new DecelerateInterpolator());
            set.setDuration(300);
            List<Animator> animatorList = new ArrayList(animators.length);
            for (Animator animator : animators) {
                if (animator != null) {
                    animatorList.add(animator);
                }
            }
            set.playTogether(animatorList);
            this.mPositionAndScaleAnimator = set;
            this.mPositionAndScaleAnimator.start();
        }
    }

    @Nullable
    private Animator prepareTranslateAnimatorForDoubleTap(float targetScale) {
        if (targetScale == 1.0f) {
            return new TranslateAnimator(0.0f, 0.0f);
        }
        return null;
    }

    @Nullable
    private Animator prepareTranslateAnimator() {
        RectF currentImageRect = getCurrentImageRect();
        if (this.mScale <= 1.0f) {
            return new TranslateAnimator(0.0f, 0.0f);
        }
        if (this.mScale <= this.mMaxScale) {
            return calculateTranslateAndCreateAnimator(currentImageRect);
        }
        return null;
    }

    @Nullable
    private Animator calculateTranslateAndCreateAnimator(RectF imageRect) {
        updateTranslates();
        RectF currentRect = imageRect;
        float translateX = this.mTranslateX;
        float translateY = this.mTranstaleY;
        if (currentRect.width() < this.mViewRect.width()) {
            translateX += this.mViewRect.centerX() - currentRect.centerX();
        } else if (CheckHelper.isLeftSideReached(imageRect, this.mViewRect)) {
            translateX -= currentRect.left - this.mViewRect.left;
        } else if (CheckHelper.isRightSideReached(currentRect, this.mViewRect)) {
            translateX += this.mViewRect.right - currentRect.right;
        }
        if (currentRect.height() < this.mViewRect.height()) {
            translateY += this.mViewRect.centerY() - currentRect.centerY();
        } else if (CheckHelper.isTopReached(imageRect, this.mViewRect)) {
            translateY -= currentRect.top - this.mViewRect.top;
        } else if (CheckHelper.isBottomReached(currentRect, this.mViewRect)) {
            translateY += this.mViewRect.bottom - currentRect.bottom;
        }
        if (translateX == this.mTranslateX && translateY == this.mTranstaleY) {
            return null;
        }
        return new TranslateAnimator(translateX, translateY);
    }

    @Nullable
    private Animator prepareScaleAnimator() {
        if (this.mScale < 1.0f) {
            return new ScaleAnimator(1.0f, this.mLastScaleFocusX, this.mLastScaleFocusY);
        }
        if (this.mScale > this.mMaxScale) {
            return new ScaleAnimator(this.mMaxScale, this.mLastScaleFocusX, this.mLastScaleFocusY);
        }
        return null;
    }

    private float alignScaleFactor(float scaleFactor, float newScale) {
        if (newScale < this.mMinScale) {
            return VelocityUtils.applyScaleVelocity(this.mScale, newScale);
        }
        if (newScale > this.mMaxScale) {
            return this.mMaxScale / this.mScale;
        }
        return scaleFactor;
    }

    private void updateTranslates() {
        this.mMatrix.getValues(this.mTempValues);
        this.mTranslateX = this.mTempValues[2];
        this.mTranstaleY = this.mTempValues[5];
    }

    public float getScale() {
        return this.mScale;
    }

    public void onFlingUpdate(int x, int y) {
        RectF currentImageRect = getCurrentImageRect();
        float dx = ((float) x) - currentImageRect.centerX();
        float dy = ((float) y) - currentImageRect.centerY();
        if ((CheckHelper.isTopReached(currentImageRect, this.mViewRect) || CheckHelper.isBottomReached(currentImageRect, this.mViewRect)) && this.mScale < 1.0f) {
            dy = 0.0f;
        }
        Timber.m751d("On fling update x: %d y: %d dx: %f dy: %f", Integer.valueOf(x), Integer.valueOf(y), Float.valueOf(dx), Float.valueOf(dy));
        this.mMatrix.postTranslate(dx, dy);
        this.mTouchListener.onAnimationUpdate();
    }

    public void onFlingEnd() {
        animateToCorrectPosition();
    }

    public void getCenterPosition(int[] position) {
        RectF currentRect = getCurrentImageRect();
        position[0] = (int) currentRect.centerX();
        position[1] = (int) currentRect.centerY();
    }

    public float getTranslationX() {
        return this.mTranslateX;
    }

    public float getTranslationY() {
        return this.mTranstaleY;
    }

    private void limitTranslations() {
        RectF currentRect = getCurrentImageRect();
        if (currentRect.width() > this.mViewRect.width()) {
            if (currentRect.right < this.mViewRect.right) {
                this.mMatrix.postTranslate(this.mViewRect.right - currentRect.right, 0.0f);
            } else if (currentRect.left > this.mViewRect.left) {
                this.mMatrix.postTranslate(this.mViewRect.left - currentRect.left, 0.0f);
            }
        }
        if (currentRect.height() <= this.mViewRect.height()) {
            return;
        }
        if (currentRect.bottom < this.mViewRect.bottom) {
            this.mMatrix.postTranslate(0.0f, this.mViewRect.bottom - currentRect.bottom);
        } else if (currentRect.top > this.mViewRect.top) {
            this.mMatrix.postTranslate(0.0f, this.mViewRect.top - currentRect.top);
        }
    }
}
