package com.hotellook.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SafeScrollToRecyclerView extends RecyclerView {
    public SafeScrollToRecyclerView(Context context) {
        super(context);
    }

    public SafeScrollToRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeScrollToRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void scrollTo(int x, int y) {
    }
}
