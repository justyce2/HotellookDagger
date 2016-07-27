package com.hotellook.ui.screen.hotel.ratingcolorizer;

import android.view.View;
import android.widget.TextView;

public class TextColorApplier implements ResourceApplier {
    public void apply(int res, View view) {
        ((TextView) view).setTextColor(res);
    }
}
