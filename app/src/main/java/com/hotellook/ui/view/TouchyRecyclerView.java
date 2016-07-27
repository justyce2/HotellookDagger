package com.hotellook.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TouchyRecyclerView extends RecyclerView {
    private OnNoChildClickListener listener;

    public interface OnNoChildClickListener {
        void onNoChildClick();
    }

    public TouchyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnNoChildClickListener(OnNoChildClickListener listener) {
        this.listener = listener;
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == 0 && findChildViewUnder(event.getX(), event.getY()) == null && this.listener != null) {
            this.listener.onNoChildClick();
        }
        return super.dispatchTouchEvent(event);
    }
}
