package com.hotellook.ui.screen.gallery;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ImagesTransitionAnimator$$Lambda$1 implements AnimatorUpdateListener {
    private final ImagesTransitionAnimator arg$1;
    private final GalleryTransitionData arg$2;
    private final GalleryTransitionData arg$3;

    private ImagesTransitionAnimator$$Lambda$1(ImagesTransitionAnimator imagesTransitionAnimator, GalleryTransitionData galleryTransitionData, GalleryTransitionData galleryTransitionData2) {
        this.arg$1 = imagesTransitionAnimator;
        this.arg$2 = galleryTransitionData;
        this.arg$3 = galleryTransitionData2;
    }

    public static AnimatorUpdateListener lambdaFactory$(ImagesTransitionAnimator imagesTransitionAnimator, GalleryTransitionData galleryTransitionData, GalleryTransitionData galleryTransitionData2) {
        return new ImagesTransitionAnimator$$Lambda$1(imagesTransitionAnimator, galleryTransitionData, galleryTransitionData2);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.arg$1.lambda$prepareViewsAndCreateAnimator$0(this.arg$2, this.arg$3, valueAnimator);
    }
}
