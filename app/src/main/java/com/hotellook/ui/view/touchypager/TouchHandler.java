package com.hotellook.ui.view.touchypager;

import android.view.MotionEvent;

public interface TouchHandler {
    boolean handleTouch(MotionEvent motionEvent);

    boolean interceptTouch(MotionEvent motionEvent);
}
