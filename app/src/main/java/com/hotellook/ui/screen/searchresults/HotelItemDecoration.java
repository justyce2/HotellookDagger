package com.hotellook.ui.screen.searchresults;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import com.hotellook.C1178R;

public class HotelItemDecoration extends ItemDecoration {
    private final int bottomOffset;
    private final int leftOffset;
    private final int rightOffset;
    private final int topOffset;

    public HotelItemDecoration(Resources resources) {
        this.topOffset = resources.getDimensionPixelOffset(C1178R.dimen.sr_top_margin);
        this.bottomOffset = resources.getDimensionPixelOffset(C1178R.dimen.sr_bottom_margin);
        this.leftOffset = resources.getDimensionPixelOffset(C1178R.dimen.sr_item_side_margin);
        this.rightOffset = resources.getDimensionPixelOffset(C1178R.dimen.sr_item_side_margin);
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        outRect.bottom = this.bottomOffset;
        outRect.left = this.leftOffset;
        outRect.right = this.rightOffset;
        outRect.top = this.topOffset;
    }
}
