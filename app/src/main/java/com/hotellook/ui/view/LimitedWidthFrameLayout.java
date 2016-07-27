package com.hotellook.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.hotellook.C1178R;

public class LimitedWidthFrameLayout extends FrameLayout {
    private final int boundedWidth;

    public LimitedWidthFrameLayout(Context context) {
        super(context);
        this.boundedWidth = 0;
    }

    public LimitedWidthFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C1178R.styleable.LimitedSizeLayout);
        this.boundedWidth = a.getDimensionPixelSize(0, 0);
        a.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if (this.boundedWidth > 0 && this.boundedWidth < measuredWidth) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.boundedWidth, MeasureSpec.getMode(widthMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
