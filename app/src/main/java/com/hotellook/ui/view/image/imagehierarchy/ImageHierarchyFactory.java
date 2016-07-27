package com.hotellook.ui.view.image.imagehierarchy;

import com.facebook.drawee.generic.GenericDraweeHierarchy;

public interface ImageHierarchyFactory {
    public static final int FADE_DURATION = 300;

    GenericDraweeHierarchy create();
}
