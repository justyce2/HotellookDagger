package com.hotellook.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class BackAwareEditText extends EditText {
    private BackPressedListener mOnImeBack;

    public interface BackPressedListener {
        void onImeBack(BackAwareEditText backAwareEditText);
    }

    public BackAwareEditText(Context context) {
        super(context);
    }

    public BackAwareEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackAwareEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == 4 && event.getAction() == 1 && this.mOnImeBack != null) {
            this.mOnImeBack.onImeBack(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setBackPressedListener(BackPressedListener listener) {
        this.mOnImeBack = listener;
    }
}
