package com.hotellook.ui.view.touchypager;

import android.view.View;

public interface VerticalScrollListener {
    boolean onFinishScroll(View view, float f);

    void onScroll(float f);

    void onStartScroll();
}
