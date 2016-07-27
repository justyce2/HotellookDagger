package com.hotellook.ui.screen.gallery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.utils.Size;

public class GalleryTransitionAnimator {
    public static final int ANIMATION_DURATION = 200;
    private AnimatorSet animator;
    private final ImageUrlCreator imageUrlCreator;
    private final ImagesTransitionAnimator imagesTransitionAnimator;
    private final View pager;
    private final View pagerBackground;
    private final Size pagerImageSize;

    /* renamed from: com.hotellook.ui.screen.gallery.GalleryTransitionAnimator.1 */
    class C12911 extends AnimatorListenerAdapter {
        final /* synthetic */ AnimationEndListener val$animationEndListener;

        C12911(AnimationEndListener animationEndListener) {
            this.val$animationEndListener = animationEndListener;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$animationEndListener.onAnimationEnd();
        }
    }

    /* renamed from: com.hotellook.ui.screen.gallery.GalleryTransitionAnimator.2 */
    class C12922 extends AnimatorListenerAdapter {
        final /* synthetic */ AnimationEndListener val$animationEndListener;

        C12922(AnimationEndListener animationEndListener) {
            this.val$animationEndListener = animationEndListener;
        }

        public void onAnimationEnd(Animator animation) {
            this.val$animationEndListener.onAnimationEnd();
        }
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }

    public GalleryTransitionAnimator(View background, SimpleDraweeView transitionView, ViewSizeCalculator sizeCalculator, View pager, ImageUrlCreator imageUrlCreator) {
        this.pagerBackground = background;
        this.pager = pager;
        this.imageUrlCreator = imageUrlCreator;
        this.pagerImageSize = sizeCalculator.calculateGalleryImageSize();
        this.imagesTransitionAnimator = new ImagesTransitionAnimator(transitionView);
    }

    public void animateToScreen(TransitionData transitionData, int index, AnimationEndListener animationEndListener) {
        if (transitionData == null) {
            animationEndListener.onAnimationEnd();
            this.pager.setVisibility(0);
            this.pagerBackground.setVisibility(0);
            return;
        }
        this.pagerBackground.setVisibility(0);
        GalleryTransitionData from = new GalleryTransitionData(transitionData, this.imageUrlCreator.getImageUrl(index, transitionData.imageSize));
        GalleryTransitionData to = new GalleryTransitionData(this.pagerImageSize, this.pagerImageSize, this.pager, this.imageUrlCreator.getImageUrl(index, this.pagerImageSize), OnScreenLocation.create(this.pager));
        BackgroundAnimatorHelper.createBackgroundInAnimator(this.pagerBackground).addListener(new C12911(animationEndListener));
        startAnimation(this.imagesTransitionAnimator.prepareViewsAndCreateAnimator(from, to), alphaAnimator);
    }

    public void animateToPreviousScreen(TransitionData transitionData, View fromView, int index, AnimationEndListener animationEndListener) {
        if (transitionData == null) {
            animationEndListener.onAnimationEnd();
            return;
        }
        GalleryTransitionData from = new GalleryTransitionData(this.pagerImageSize, this.pagerImageSize, fromView, this.imageUrlCreator.getImageUrl(index, this.pagerImageSize), OnScreenLocation.create(fromView));
        GalleryTransitionData to = new GalleryTransitionData(transitionData, this.imageUrlCreator.getImageUrl(index, transitionData.imageSize));
        BackgroundAnimatorHelper.createBackgroundOutAnimator(this.pagerBackground).addListener(new C12922(animationEndListener));
        startAnimation(this.imagesTransitionAnimator.prepareViewsAndCreateAnimator(from, to), alphaAnimator);
    }

    private void startAnimation(Animator... animators) {
        if (this.animator != null && this.animator.isRunning()) {
            this.animator.removeAllListeners();
            this.animator.cancel();
        }
        this.animator = new AnimatorSet();
        this.animator.setInterpolator(new DecelerateInterpolator());
        this.animator.setDuration(200);
        this.animator.playTogether(animators);
        this.animator.start();
    }
}
