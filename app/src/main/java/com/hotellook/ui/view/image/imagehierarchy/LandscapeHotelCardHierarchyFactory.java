package com.hotellook.ui.view.image.imagehierarchy;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.hotellook.C1178R;
import java.util.ArrayList;
import java.util.List;

public class LandscapeHotelCardHierarchyFactory implements ImageHierarchyFactory {
    private final Resources res;

    public LandscapeHotelCardHierarchyFactory(Resources res) {
        this.res = res;
    }

    public GenericDraweeHierarchy create() {
        List<Drawable> overlays = new ArrayList(1);
        overlays.add(this.res.getDrawable(C1178R.drawable.sr_overlay));
        return new GenericDraweeHierarchyBuilder(this.res).setFadeDuration(ImageHierarchyFactory.FADE_DURATION).setBackground(new ColorDrawable(this.res.getColor(C1178R.color.sr_item_bkg))).setOverlays(overlays).build();
    }
}
