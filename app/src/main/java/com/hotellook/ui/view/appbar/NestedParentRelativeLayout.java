package com.hotellook.ui.view.appbar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class NestedParentRelativeLayout extends RelativeLayout implements NestedScrollingParent, NestedParent {
    private OnNestedScrollListener onNestedScrollListener;

    /* renamed from: com.hotellook.ui.view.appbar.NestedParentRelativeLayout.1 */
    class C14151 implements OnNestedScrollListener {
        C14151() {
        }

        public int onNestedPreScroll(int dy) {
            return 0;
        }

        public void onStopNestedScroll() {
        }
    }

    public NestedParentRelativeLayout(Context context) {
        super(context);
        this.onNestedScrollListener = createNullNestedScrollListener();
    }

    public NestedParentRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.onNestedScrollListener = createNullNestedScrollListener();
    }

    public NestedParentRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.onNestedScrollListener = createNullNestedScrollListener();
    }

    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        consumed[1] = this.onNestedScrollListener.onNestedPreScroll(dy);
    }

    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    public void onStopNestedScroll(View child) {
        this.onNestedScrollListener.onStopNestedScroll();
    }

    private OnNestedScrollListener createNullNestedScrollListener() {
        return new C14151();
    }

    public void setOnNestedScrollListener(@NonNull OnNestedScrollListener onNestedScrollListener) {
        this.onNestedScrollListener = onNestedScrollListener;
    }

    public void onNestedScrollAccepted(View child, View target, int axes) {
    }

    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }
}
