package com.hotellook.ui.screen.hotel;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerWithExtendedMotionInterception extends ViewPager {
    private int bottomInterceptZone;
    private int leftInterceptZone;
    private int rightInterceptZone;
    private int topInterceptZone;

    public ViewPagerWithExtendedMotionInterception(Context context) {
        super(context);
    }

    public ViewPagerWithExtendedMotionInterception(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPaddingWithInterception(int left, int top, int right, int bottom) {
        setPadding(left, top, right, bottom);
        this.leftInterceptZone = left;
        this.topInterceptZone = top;
        this.rightInterceptZone = right;
        this.bottomInterceptZone = bottom;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getX() < ((float) this.leftInterceptZone)) {
            ev.setLocation((float) this.leftInterceptZone, ev.getY());
        }
        if (ev.getX() > ((float) (getWidth() - this.rightInterceptZone))) {
            ev.setLocation((float) ((getWidth() - this.rightInterceptZone) - 1), ev.getY());
        }
        return super.onInterceptTouchEvent(ev);
    }
}
