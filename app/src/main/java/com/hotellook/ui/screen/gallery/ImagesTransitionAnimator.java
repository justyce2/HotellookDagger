package com.hotellook.ui.screen.gallery;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils.ScaleType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.common.view.ScreenDelta;

public class ImagesTransitionAnimator {
    private final SimpleDraweeView transitionView;

    /* renamed from: com.hotellook.ui.screen.gallery.ImagesTransitionAnimator.1 */
    class C12931 extends AnimatorListenerAdapter {
        final /* synthetic */ GalleryTransitionData val$fromState;
        final /* synthetic */ GalleryTransitionData val$toState;

        C12931(GalleryTransitionData galleryTransitionData, GalleryTransitionData galleryTransitionData2) {
            this.val$fromState = galleryTransitionData;
            this.val$toState = galleryTransitionData2;
        }

        public void onAnimationEnd(Animator animation) {
            ImagesTransitionAnimator.this.onAnimationEnd(this.val$fromState, this.val$toState);
        }
    }

    public ImagesTransitionAnimator(SimpleDraweeView transitionView) {
        this.transitionView = transitionView;
        transitionView.setHierarchy(new GenericDraweeHierarchyBuilder(null).setFadeDuration(0).setActualImageScaleType(ScaleType.CENTER_CROP).build());
    }

    public Animator prepareViewsAndCreateAnimator(GalleryTransitionData fromState, GalleryTransitionData toState) {
        prepareViews(fromState, toState);
        ScreenDelta deltaToTargetView = new ScreenDelta(toState.screenLocation, OnScreenLocation.create(this.transitionView));
        setTransitionViewTranslates(fromState);
        Animator translateYAnimator = ObjectAnimator.ofFloat(this.transitionView, View.TRANSLATION_Y, new float[]{(float) deltaToTargetView.yDelta});
        Animator translateXAnimator = ObjectAnimator.ofFloat(this.transitionView, View.TRANSLATION_X, new float[]{(float) deltaToTargetView.xDelta});
        ValueAnimator.ofFloat(new float[]{0.0f, 1.0f}).addUpdateListener(ImagesTransitionAnimator$$Lambda$1.lambdaFactory$(this, fromState, toState));
        AnimatorSet animator = new AnimatorSet();
        animator.playTogether(new Animator[]{translateXAnimator, translateYAnimator, sizeAnimator});
        animator.addListener(new C12931(fromState, toState));
        return animator;
    }

    /* synthetic */ void lambda$prepareViewsAndCreateAnimator$0(GalleryTransitionData fromState, GalleryTransitionData toState, ValueAnimator animation) {
        float fraction = animation.getAnimatedFraction();
        this.transitionView.getLayoutParams().height = (int) (((float) fromState.viewSize.getHeight()) + (((float) (toState.viewSize.getHeight() - fromState.viewSize.getHeight())) * fraction));
        this.transitionView.getLayoutParams().width = (int) (((float) fromState.viewSize.getWidth()) + (((float) (toState.viewSize.getWidth() - fromState.viewSize.getWidth())) * fraction));
        this.transitionView.requestLayout();
    }

    private void prepareViews(GalleryTransitionData fromState, GalleryTransitionData toState) {
        hideViews(fromState, toState);
        resetTransitionDrawee(fromState);
        setImageToTransitionDrawee(fromState.url);
    }

    private void onAnimationEnd(GalleryTransitionData fromState, GalleryTransitionData toState) {
        this.transitionView.setVisibility(8);
        if (fromState.view != null) {
            fromState.view.setVisibility(8);
        }
        View targetView = toState.view;
        if (targetView != null) {
            targetView.setVisibility(0);
            if (targetView instanceof SimpleDraweeView) {
                setImagesToTargetView((SimpleDraweeView) targetView, fromState.url);
            }
        }
    }

    private void hideViews(GalleryTransitionData fromState, GalleryTransitionData toState) {
        if (fromState.view != null) {
            fromState.view.setVisibility(8);
        }
        if (toState.view != null) {
            toState.view.setVisibility(8);
        }
    }

    private void setTransitionViewTranslates(GalleryTransitionData fromState) {
        ScreenDelta delta = new ScreenDelta(fromState.screenLocation, OnScreenLocation.create(this.transitionView));
        this.transitionView.setTranslationY((float) delta.yDelta);
        this.transitionView.setTranslationX((float) delta.xDelta);
    }

    private void resetTransitionDrawee(GalleryTransitionData galleryTransitionData) {
        View view = this.transitionView;
        view.getLayoutParams().width = galleryTransitionData.viewSize.getWidth();
        view.getLayoutParams().height = galleryTransitionData.viewSize.getHeight();
        view.setTranslationY(0.0f);
        view.setTranslationX(0.0f);
        ((LayoutParams) view.getLayoutParams()).gravity = 17;
        view.setVisibility(0);
        view.requestLayout();
    }

    private void setImagesToTargetView(SimpleDraweeView targetView, String fromUrl) {
        targetView.setController(Fresco.newDraweeControllerBuilder().setUri(Uri.parse(fromUrl)).build());
    }

    private void setImageToTransitionDrawee(String fromUrl) {
        setImagesToTargetView(this.transitionView, fromUrl);
        this.transitionView.setVisibility(0);
    }
}
