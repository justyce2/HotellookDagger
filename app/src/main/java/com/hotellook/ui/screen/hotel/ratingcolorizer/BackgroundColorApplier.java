package com.hotellook.ui.screen.hotel.ratingcolorizer;

import android.graphics.drawable.GradientDrawable;
import android.view.View;

public class BackgroundColorApplier implements ResourceApplier {
    public void apply(int res, View view) {
        ((GradientDrawable) view.getBackground()).setColor(res);
    }
}
