package com.hotellook.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class AdvancedScrollView extends ScrollView {
    private static final int SCROLL_STOP_CHECK_TIMEOUT = 100;
    private int initialPosition;
    private OnScrollStoppedListener onScrollStoppedListener;
    private OnScrollListener scrollViewListener;
    private Runnable scrollerTask;

    /* renamed from: com.hotellook.ui.view.AdvancedScrollView.1 */
    class C13981 implements Runnable {
        C13981() {
        }

        public void run() {
            if (!hasStopped(AdvancedScrollView.this.getScrollY())) {
                AdvancedScrollView.this.initialPosition = AdvancedScrollView.this.getScrollY();
                AdvancedScrollView.this.postDelayed(AdvancedScrollView.this.scrollerTask, 100);
            } else if (AdvancedScrollView.this.onScrollStoppedListener != null) {
                AdvancedScrollView.this.onScrollStoppedListener.onScrollStopped();
            }
        }

        private boolean hasStopped(int newPosition) {
            return AdvancedScrollView.this.initialPosition - newPosition == 0;
        }
    }

    public interface OnScrollListener {
        void onScrollChanged(View view, int i, int i2, int i3, int i4);
    }

    public interface OnScrollStoppedListener {
        void onScrollStopped();
    }

    public AdvancedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.scrollViewListener = null;
        this.scrollerTask = new C13981();
    }

    public void setOnScrollStoppedListener(OnScrollStoppedListener listener) {
        this.onScrollStoppedListener = listener;
    }

    public void startScrollerTask() {
        this.initialPosition = getScrollY();
        postDelayed(this.scrollerTask, 100);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 1) {
            startScrollerTask();
        }
        return super.onTouchEvent(ev);
    }

    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (this.scrollViewListener != null) {
            this.scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public void fling(int velocityY) {
        super.fling(velocityY);
    }

    public void setOnScrollLister(OnScrollListener onScrollLister) {
        this.scrollViewListener = onScrollLister;
    }
}
