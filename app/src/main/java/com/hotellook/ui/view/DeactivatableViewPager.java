package com.hotellook.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class DeactivatableViewPager extends ViewPager {
    private boolean pagingEnabled;

    public DeactivatableViewPager(Context context) {
        super(context);
        this.pagingEnabled = false;
    }

    public DeactivatableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pagingEnabled = false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return this.pagingEnabled && super.onTouchEvent(ev);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.pagingEnabled && super.onInterceptTouchEvent(ev);
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    public boolean isPageable() {
        return this.pagingEnabled;
    }
}
