package com.hotellook.ui.screen.hotel;

import android.animation.LayoutTransition;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.hotellook.ui.view.LimitedWidthFrameLayout;
import timber.log.Timber;

public class LoadableLayout extends LimitedWidthFrameLayout {
    private View currentState;

    public LoadableLayout(Context context) {
        super(context);
    }

    public LoadableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void setUp() {
        setLayoutTransition(new LayoutTransition());
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void showView(View stateRoot) {
        Timber.m751d("entering state: %d", Integer.valueOf(stateRoot.getId()));
        if (!(this.currentState == null || this.currentState == stateRoot)) {
            this.currentState.setVisibility(8);
        }
        stateRoot.setVisibility(0);
        this.currentState = stateRoot;
    }

    public View getCurrentState() {
        return this.currentState;
    }
}
