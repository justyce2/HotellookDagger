package com.hotellook.ui.screen.searchresults.adapters.itemsize;

import android.view.View;

public interface ItemLayoutParamsGenerator {
    void generateFullWidthItemsParams(View view);

    ListItemImageParams generateHotelItemParams(int i, int i2);
}
