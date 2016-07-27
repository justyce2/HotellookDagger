package com.hotellook.ui.view.image.imagehierarchy;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.hotellook.C1178R;

public class PortraitDefaultHierarchyFactory implements ImageHierarchyFactory {
    private final Resources res;

    public PortraitDefaultHierarchyFactory(Resources res) {
        this.res = res;
    }

    public GenericDraweeHierarchy create() {
        return new GenericDraweeHierarchyBuilder(this.res).setFadeDuration(ImageHierarchyFactory.FADE_DURATION).setProgressBarImage(LoadDrawableFactory.create()).setBackground(new ColorDrawable(this.res.getColor(C1178R.color.sr_item_bkg))).build();
    }
}
