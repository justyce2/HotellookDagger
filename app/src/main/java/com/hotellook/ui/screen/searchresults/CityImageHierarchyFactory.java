package com.hotellook.ui.screen.searchresults;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.hotellook.C1178R;
import java.util.ArrayList;
import java.util.List;

public class CityImageHierarchyFactory {
    private static final int FADE_DURATION = 300;
    private final Resources res;

    public CityImageHierarchyFactory(Resources res) {
        this.res = res;
    }

    public GenericDraweeHierarchy newHierarchy() {
        List<Drawable> overlays = new ArrayList(1);
        overlays.add(this.res.getDrawable(C1178R.drawable.favorite_city_overlay));
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(this.res);
        builder.setFadeDuration(FADE_DURATION).setBackground(new ColorDrawable(this.res.getColor(C1178R.color.sr_item_bkg))).setOverlays(overlays);
        return builder.build();
    }
}
