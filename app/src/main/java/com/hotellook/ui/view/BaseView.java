package com.hotellook.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class BaseView extends RelativeLayout {
    static final String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    static final String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
    boolean animation;
    int beforeBackground;
    final int disabledBackgroundColor;
    public boolean isLastTouch;

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.disabledBackgroundColor = Color.parseColor("#E2E2E2");
        this.isLastTouch = false;
        this.animation = false;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            setBackgroundColor(this.beforeBackground);
        } else {
            setBackgroundColor(this.disabledBackgroundColor);
        }
        invalidate();
    }

    protected void onAnimationStart() {
        super.onAnimationStart();
        this.animation = true;
    }

    protected void onAnimationEnd() {
        super.onAnimationEnd();
        this.animation = false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.animation) {
            invalidate();
        }
    }
}
