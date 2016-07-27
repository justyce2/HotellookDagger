package com.hotellook.ui.view.viewmovers;

import android.view.View.OnTouchListener;

public interface ScrollableWrapper {
    void listenScrolls(ScrollListener scrollListener);

    void listenTouches(OnTouchListener onTouchListener);

    int scrollY();
}
