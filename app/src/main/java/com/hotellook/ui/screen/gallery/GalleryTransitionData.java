package com.hotellook.ui.screen.gallery;

import android.support.annotation.Nullable;
import android.view.View;
import com.hotellook.common.view.OnScreenLocation;
import com.hotellook.utils.Size;

public class GalleryTransitionData {
    public final Size imageSize;
    public final OnScreenLocation screenLocation;
    public final String url;
    @Nullable
    public final View view;
    public final Size viewSize;

    public GalleryTransitionData(Size imageSize, Size viewSize, @Nullable View view, String url, OnScreenLocation screenLocation) {
        this.viewSize = viewSize;
        this.imageSize = imageSize;
        this.view = view;
        this.url = url;
        this.screenLocation = screenLocation;
    }

    public GalleryTransitionData(TransitionData transitionData, String url) {
        this.viewSize = transitionData.viewSize;
        this.imageSize = transitionData.imageSize;
        this.view = null;
        this.url = url;
        this.screenLocation = transitionData.imageLocation;
    }
}
