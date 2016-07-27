package com.hotellook.utils.sdk;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PatchyViewPager extends ViewPager {
    public PatchyViewPager(Context context) {
        super(context);
    }

    public PatchyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e2) {
            return false;
        }
    }
}
