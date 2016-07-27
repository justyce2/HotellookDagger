package com.hotellook.ui.screen.searchresults.adapters;

import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.View;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ListItemImageParams;
import com.hotellook.utils.Size;

public class GridItemLayoutParamsGenerator implements ItemLayoutParamsGenerator {
    private final Size imageSize;
    private final int span;

    public GridItemLayoutParamsGenerator(Size imageSize, int span) {
        this.imageSize = imageSize;
        this.span = span;
    }

    public ListItemImageParams generateHotelItemParams(int index, int count) {
        int height = this.imageSize.getHeight() / 2;
        LayoutParams layoutParams = new LayoutParams(-2, height);
        layoutParams.setFullSpan(false);
        return new ListItemImageParams(layoutParams, new Size(this.imageSize.getWidth() / this.span, height));
    }

    public void generateFullWidthItemsParams(View view) {
        LayoutParams layoutParams = new LayoutParams(view.getLayoutParams());
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }
}
