package com.hotellook.ui.view.image.imagehierarchy;

import android.content.res.Resources;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;

public class GalleryHierarchyFactory implements ImageHierarchyFactory {
    private final Resources res;

    public GalleryHierarchyFactory(Resources res) {
        this.res = res;
    }

    public GenericDraweeHierarchy create() {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(this.res);
        builder.setFadeDuration(ImageHierarchyFactory.FADE_DURATION);
        builder.setProgressBarImage(LoadDrawableFactory.create());
        return builder.build();
    }
}
