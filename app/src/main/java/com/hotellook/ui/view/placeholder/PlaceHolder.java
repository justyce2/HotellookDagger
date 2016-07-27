package com.hotellook.ui.view.placeholder;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.hotellook.C1178R;

public class PlaceHolder extends FrameLayout {
    private TextView mButton;

    public PlaceHolder(Context context) {
        super(context);
        init(context, null);
    }

    public PlaceHolder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PlaceHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(C1178R.layout.place_holder, this, true);
            TextView message = (TextView) findViewById(C1178R.id.placeholder_message);
            this.mButton = (TextView) findViewById(C1178R.id.placeholder_button);
            TypedArray attributes = context.obtainStyledAttributes(attrs, C1178R.styleable.PlaceHolder, 0, 0);
            String messageText = attributes.getString(0);
            String buttonText = attributes.getString(1);
            message.setText(messageText);
            if (TextUtils.isEmpty(buttonText)) {
                this.mButton.setVisibility(8);
            } else {
                this.mButton.setText(buttonText);
            }
            attributes.recycle();
            setBackgroundColor(getResources().getColor(C1178R.color.orange_F2AC4B));
        }
    }

    public void setOnButtonClickListener(OnClickListener clickListener) {
        this.mButton.setOnClickListener(clickListener);
    }
}
