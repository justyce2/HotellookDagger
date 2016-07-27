package com.hotellook.ui.screen.gallery;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PhotosGalleryFragment$$Lambda$2 implements Runnable {
    private final PhotosGalleryFragment arg$1;

    private PhotosGalleryFragment$$Lambda$2(PhotosGalleryFragment photosGalleryFragment) {
        this.arg$1 = photosGalleryFragment;
    }

    public static Runnable lambdaFactory$(PhotosGalleryFragment photosGalleryFragment) {
        return new PhotosGalleryFragment$$Lambda$2(photosGalleryFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$postTransitionAnimation$2();
    }
}
