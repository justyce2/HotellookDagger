package com.hotellook.ui.view.touchypager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.hotellook.common.view.OnScreenLocation;
import timber.log.Timber;

public class TouchyViewPager extends ViewPager {
    private TouchyDraweeView currentView;
    private FingerToPagerShift fingerToPagerShift;
    private boolean gestureDelegated;
    private OnScreenLocation pagerCenter;
    private float prevViewCenterX;

    public TouchyViewPager(Context context) {
        super(context);
        this.gestureDelegated = false;
        this.fingerToPagerShift = new FingerToPagerShift();
    }

    public TouchyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.gestureDelegated = false;
        this.fingerToPagerShift = new FingerToPagerShift();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.fingerToPagerShift.onNewEvent(ev);
        if (this.pagerCenter == null) {
            this.pagerCenter = OnScreenLocation.create(this);
        }
        boolean handled = false;
        if (this.currentView != null && (this.gestureDelegated || isInCenter(ev))) {
            Timber.m751d("OnTouch: in center", new Object[0]);
            handled = this.currentView.interceptTouch(ev);
            Timber.m751d("OnTouch: intercepted by child %b", Boolean.valueOf(handled));
            if (handled) {
                handled = this.currentView.handleTouch(ev);
                Timber.m751d("OnTouch: handled by child %b", Boolean.valueOf(handled));
                if (handled) {
                    this.fingerToPagerShift.onHandledByChild();
                }
            }
        }
        if (!handled || CheckHelper.isTouchFinished(ev.getAction())) {
            this.gestureDelegated = false;
        }
        Timber.m751d("Intercepted  by viewpager child %b", Boolean.valueOf(handled));
        if (!handled) {
            requestDisallowInterceptTouchEvent(false);
            ev.setLocation(ev.getX() + this.fingerToPagerShift.getShiftX(), ev.getY());
            try {
                handled = super.onTouchEvent(ev);
            } catch (Exception e) {
                Timber.m751d("Exception onTouchEvent ", new Object[0]);
                e.printStackTrace();
            }
            Timber.m751d("OnTouch: intercepted by viewpager %b", Boolean.valueOf(handled));
        }
        Timber.m751d("Handled touch by viewpager %b", Boolean.valueOf(handled));
        return handled;
    }

    private boolean isInCenter(MotionEvent ev) {
        boolean overscrolledThroughPagerCenter;
        boolean coordinateInCenter;
        float currentX = ev.getX();
        if (CheckHelper.isTouchStarted(ev.getAction() & 255)) {
            this.prevViewCenterX = currentX;
        }
        OnScreenLocation location = OnScreenLocation.create(this.currentView);
        int delta = Math.abs(this.pagerCenter.contentCenterX - location.viewCenterX);
        if ((((float) this.pagerCenter.contentCenterX) - this.prevViewCenterX) * ((float) (this.pagerCenter.contentCenterX - location.viewCenterX)) < 0.0f) {
            overscrolledThroughPagerCenter = true;
        } else {
            overscrolledThroughPagerCenter = false;
        }
        if (!this.gestureDelegated) {
            this.gestureDelegated = overscrolledThroughPagerCenter;
        }
        if (delta < 2) {
            coordinateInCenter = true;
        } else {
            coordinateInCenter = false;
        }
        Timber.m751d("OnTouch: coordinates in center %b", Boolean.valueOf(coordinateInCenter));
        Timber.m751d("OnTouch: center overscrolled  %b", Boolean.valueOf(overscrolledThroughPagerCenter));
        this.prevViewCenterX = (float) location.viewCenterX;
        if (coordinateInCenter || overscrolledThroughPagerCenter) {
            return true;
        }
        return false;
    }

    public void setCurrentView(TouchyDraweeView currentView) {
        this.currentView = currentView;
    }
}
