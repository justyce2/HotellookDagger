package com.hotellook.ui.view.touchypager;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class SafeGestureDetector extends GestureDetector {
    private boolean waitForActionDown;

    public SafeGestureDetector(Context context, OnGestureListener listener) {
        super(context, listener);
        this.waitForActionDown = true;
    }

    public SafeGestureDetector(Context context, OnGestureListener listener, Handler handler) {
        super(context, listener, handler);
        this.waitForActionDown = true;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (CheckHelper.isTouchStarted(action)) {
            this.waitForActionDown = false;
        }
        boolean result = false;
        if (!this.waitForActionDown) {
            result = super.onTouchEvent(ev);
        }
        if (CheckHelper.isTouchFinished(action)) {
            this.waitForActionDown = true;
        }
        return result;
    }
}
