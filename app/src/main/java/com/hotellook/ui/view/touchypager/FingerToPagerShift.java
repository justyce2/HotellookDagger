package com.hotellook.ui.view.touchypager;

import android.view.MotionEvent;

public class FingerToPagerShift {
    private float mCurrentX;
    private float mEndX;
    private float mStartX;

    public void onNewEvent(MotionEvent event) {
        int action = event.getAction();
        if (CheckHelper.isTouchStarted(action) || CheckHelper.isTouchFinished(action)) {
            float x = event.getX();
            this.mEndX = x;
            this.mStartX = x;
        }
        this.mCurrentX = event.getX();
    }

    public void onHandledByChild() {
        this.mEndX = this.mCurrentX;
    }

    public float getShiftX() {
        return this.mStartX - this.mEndX;
    }
}
