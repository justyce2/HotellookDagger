package com.hotellook.ui.screen.searchresults.adapters;

import android.graphics.Point;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.view.View;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ItemLayoutParamsGenerator;
import com.hotellook.ui.screen.searchresults.adapters.itemsize.ListItemImageParams;
import com.hotellook.utils.Size;

public class PhoneGridItemsParamsGenerator implements ItemLayoutParamsGenerator {
    private final Point displaySize;
    private final Size imageSize;
    private final int span;

    public PhoneGridItemsParamsGenerator(Size imageSize, Point displaySize) {
        this(imageSize, 2, displaySize);
    }

    public PhoneGridItemsParamsGenerator(Size imageSize, int span, Point displaySize) {
        this.imageSize = imageSize;
        this.span = span;
        this.displaySize = displaySize;
    }

    public ListItemImageParams generateHotelItemParams(int index, int count) {
        if (count == 1) {
            return generateCenteredParams();
        }
        int height = this.imageSize.getHeight();
        LayoutParams layoutParams = new LayoutParams(-2, height);
        layoutParams.setFullSpan(false);
        return new ListItemImageParams(layoutParams, new Size(this.imageSize.getWidth() / this.span, height));
    }

    private ListItemImageParams generateCenteredParams() {
        int height = this.imageSize.getHeight();
        LayoutParams layoutParams = new LayoutParams(this.imageSize.getWidth(), height);
        layoutParams.setFullSpan(true);
        int imageWidth = this.imageSize.getWidth();
        int margin = (this.displaySize.x - imageWidth) / 2;
        layoutParams.leftMargin = margin;
        layoutParams.rightMargin = margin;
        return new ListItemImageParams(layoutParams, new Size(imageWidth, height));
    }

    public void generateFullWidthItemsParams(View view) {
        LayoutParams layoutParams = new LayoutParams(view.getLayoutParams());
        layoutParams.setFullSpan(true);
        view.setLayoutParams(layoutParams);
    }
}
