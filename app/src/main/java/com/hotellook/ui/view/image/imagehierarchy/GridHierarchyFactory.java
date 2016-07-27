package com.hotellook.ui.view.image.imagehierarchy;

import android.content.res.Resources;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.hotellook.C1178R;

public class GridHierarchyFactory implements ImageHierarchyFactory {
    private final Resources res;

    public GridHierarchyFactory(Resources res) {
        this.res = res;
    }

    public GenericDraweeHierarchy create() {
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(this.res);
        builder.setFadeDuration(ImageHierarchyFactory.FADE_DURATION);
        builder.setBackground(this.res.getDrawable(C1178R.drawable.grid_item_bkg));
        return builder.build();
    }
}
