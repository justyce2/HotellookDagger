package com.hotellook.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import com.hotellook.C1178R;

public class LimitedSizeLinearLayout extends LinearLayout {
    private final int maxHeight;
    private final int maxWidth;

    public LimitedSizeLinearLayout(Context context) {
        super(context);
        this.maxWidth = 0;
        this.maxHeight = 0;
    }

    public LimitedSizeLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, C1178R.styleable.LimitedSizeLayout);
        this.maxWidth = a.getDimensionPixelSize(0, 0);
        this.maxHeight = a.getDimensionPixelSize(1, 0);
        a.recycle();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getLimitedMeasureSpec(widthMeasureSpec, this.maxWidth), getLimitedMeasureSpec(heightMeasureSpec, this.maxHeight));
    }

    private int getLimitedMeasureSpec(int measureSpec, int maxSize) {
        int measuredSize = MeasureSpec.getSize(measureSpec);
        if (maxSize <= 0 || maxSize >= measuredSize) {
            return measureSpec;
        }
        return MeasureSpec.makeMeasureSpec(maxSize, MeasureSpec.getMode(measureSpec));
    }
}
