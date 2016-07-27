package com.hotellook.ui.screen.searchresults;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.hotellook.ui.screen.searchresults.HotelsGalleryFragment.GalleryViewPagerListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelsGalleryFragment$GalleryViewPagerListener$$Lambda$1 implements OnTouchListener {
    private final GalleryViewPagerListener arg$1;
    private final int arg$2;

    private HotelsGalleryFragment$GalleryViewPagerListener$$Lambda$1(GalleryViewPagerListener galleryViewPagerListener, int i) {
        this.arg$1 = galleryViewPagerListener;
        this.arg$2 = i;
    }

    public static OnTouchListener lambdaFactory$(GalleryViewPagerListener galleryViewPagerListener, int i) {
        return new HotelsGalleryFragment$GalleryViewPagerListener$$Lambda$1(galleryViewPagerListener, i);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$setOnClickListener$0(this.arg$2, view, motionEvent);
    }
}
