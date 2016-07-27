package com.hotellook.ui.screen.searchresults;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelsGalleryFragment$$Lambda$1 implements Runnable {
    private final HotelsGalleryFragment arg$1;

    private HotelsGalleryFragment$$Lambda$1(HotelsGalleryFragment hotelsGalleryFragment) {
        this.arg$1 = hotelsGalleryFragment;
    }

    public static Runnable lambdaFactory$(HotelsGalleryFragment hotelsGalleryFragment) {
        return new HotelsGalleryFragment$$Lambda$1(hotelsGalleryFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onViewCreated$0();
    }
}
