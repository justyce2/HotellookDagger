package com.hotellook.ui.screen.searchresults;

import com.hotellook.ui.screen.searchresults.HotelsGalleryFragment.GalleryViewPagerListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelsGalleryFragment$$Lambda$2 implements Runnable {
    private final HotelsGalleryFragment arg$1;
    private final GalleryViewPagerListener arg$2;

    private HotelsGalleryFragment$$Lambda$2(HotelsGalleryFragment hotelsGalleryFragment, GalleryViewPagerListener galleryViewPagerListener) {
        this.arg$1 = hotelsGalleryFragment;
        this.arg$2 = galleryViewPagerListener;
    }

    public static Runnable lambdaFactory$(HotelsGalleryFragment hotelsGalleryFragment, GalleryViewPagerListener galleryViewPagerListener) {
        return new HotelsGalleryFragment$$Lambda$2(hotelsGalleryFragment, galleryViewPagerListener);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$setUpViewPager$1(this.arg$2);
    }
}
