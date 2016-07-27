package com.hotellook.ui.view.touchypager;

import android.view.View;

public class NullVerticalScrollListener implements VerticalScrollListener {
    public void onStartScroll() {
    }

    public void onScroll(float translateY) {
    }

    public boolean onFinishScroll(View v, float translateY) {
        return true;
    }
}
