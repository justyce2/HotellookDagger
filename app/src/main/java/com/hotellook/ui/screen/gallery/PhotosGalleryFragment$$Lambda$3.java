package com.hotellook.ui.screen.gallery;

import com.hotellook.ui.screen.gallery.GalleryTransitionAnimator.AnimationEndListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PhotosGalleryFragment$$Lambda$3 implements AnimationEndListener {
    private final PhotosGalleryFragment arg$1;

    private PhotosGalleryFragment$$Lambda$3(PhotosGalleryFragment photosGalleryFragment) {
        this.arg$1 = photosGalleryFragment;
    }

    public static AnimationEndListener lambdaFactory$(PhotosGalleryFragment photosGalleryFragment) {
        return new PhotosGalleryFragment$$Lambda$3(photosGalleryFragment);
    }

    @Hidden
    public void onAnimationEnd() {
        this.arg$1.lambda$null$1();
    }
}
