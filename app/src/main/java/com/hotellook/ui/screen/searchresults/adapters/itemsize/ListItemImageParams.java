package com.hotellook.ui.screen.searchresults.adapters.itemsize;

import android.support.v7.widget.RecyclerView.LayoutParams;
import com.hotellook.utils.Size;

public class ListItemImageParams {
    public final Size imageSize;
    public final LayoutParams layoutParams;

    public ListItemImageParams(LayoutParams layoutParams, Size imageSize) {
        this.layoutParams = layoutParams;
        this.imageSize = imageSize;
    }
}
