package com.hotellook.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import com.devspark.robototextview.widget.RobotoTextView;

public class AutoResizeRobotoTextView extends RobotoTextView {
    private int minTextSize;

    public AutoResizeRobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.minTextSize = 0;
        init();
    }

    public AutoResizeRobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.minTextSize = 0;
        init();
    }

    public AutoResizeRobotoTextView(Context context) {
        super(context);
        this.minTextSize = 0;
        init();
    }

    private void init() {
        setSingleLine();
        setEllipsize(TruncateAt.END);
    }

    @SuppressLint({"WrongCall"})
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout != null) {
            int lineCount = layout.getLineCount();
            if (lineCount > 0 && layout.getEllipsisCount(lineCount - 1) > 0) {
                float newTextSize = getTextSize() - 1.0f;
                if (newTextSize > ((float) this.minTextSize)) {
                    setTextSize(0, newTextSize);
                    onMeasure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }

    public void setMinTextSize(int minTextSize) {
        this.minTextSize = minTextSize;
    }
}
