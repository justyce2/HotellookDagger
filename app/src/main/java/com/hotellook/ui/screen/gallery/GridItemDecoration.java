package com.hotellook.ui.screen.gallery;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class GridItemDecoration extends ItemDecoration {
    private final int offset;
    private final int offsetInCenter;
    private final int spanCount;

    public GridItemDecoration(int viewWidth, int itemWidth, int spanCount) {
        this.spanCount = spanCount;
        int fullOffsetSize = viewWidth - (spanCount * itemWidth);
        this.offset = fullOffsetSize / (spanCount - 1);
        this.offsetInCenter = this.offset + (fullOffsetSize % (spanCount - 1));
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (isCenterItemInRow(view, parent)) {
            outRect.right = this.offsetInCenter;
        } else if (isLastItemInRow(view, parent)) {
            outRect.right = 0;
        } else {
            outRect.right = this.offset;
        }
        outRect.bottom = this.offset;
    }

    private boolean isLastItemInRow(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) % this.spanCount == this.spanCount + -1;
    }

    private boolean isCenterItemInRow(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) % this.spanCount == (this.spanCount + -1) / 2;
    }
}
