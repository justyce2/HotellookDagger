package com.hotellook.ui.screen.searchprogress;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;
import com.hotellook.C1178R;

public class GateListItemDecoration extends ItemDecoration {
    private final int itemOffset;

    public GateListItemDecoration(@NonNull Context context) {
        this.itemOffset = context.getResources().getDimensionPixelSize(C1178R.dimen.gate_list_item_offset);
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (parent.getLayoutManager().getPosition(view) != 0) {
            outRect.set(0, this.itemOffset, 0, 0);
        }
    }
}
