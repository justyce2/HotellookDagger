package com.hotellook.ui.screen.filters.radiogroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;
import me.zhanghai.android.materialprogressbar.C1759R;

public class FiltersRadioButton extends FrameLayout {
    private boolean groupMode;
    private int layoutId;
    private OnCheckedChangeListener onCheckedChangeListener;
    private RadioButton radioButton;
    @Nullable
    private TextView subTitle;
    private TextView title;

    /* renamed from: com.hotellook.ui.screen.filters.radiogroup.FiltersRadioButton.1 */
    class C12881 extends MonkeySafeClickListener {
        C12881() {
        }

        public void onSafeClick(View v) {
            boolean checked = true;
            if (FiltersRadioButton.this.groupMode) {
                FiltersRadioButton.this.radioButton.setChecked(true);
                if (FiltersRadioButton.this.onCheckedChangeListener != null) {
                    FiltersRadioButton.this.onCheckedChangeListener.onCheckedChanged(FiltersRadioButton.this, true);
                    return;
                }
                return;
            }
            if (FiltersRadioButton.this.radioButton.isChecked()) {
                checked = false;
            }
            FiltersRadioButton.this.radioButton.setChecked(checked);
            if (FiltersRadioButton.this.onCheckedChangeListener != null) {
                FiltersRadioButton.this.onCheckedChangeListener.onCheckedChanged(FiltersRadioButton.this, checked);
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean z);
    }

    public FiltersRadioButton(Context context, int layoutId) {
        super(context);
        this.groupMode = false;
        this.layoutId = C1178R.layout.radiobutton_one_title;
        this.layoutId = layoutId;
        init(context, null);
    }

    public FiltersRadioButton(Context context) {
        super(context);
        this.groupMode = false;
        this.layoutId = C1178R.layout.radiobutton_one_title;
        init(context, null);
    }

    public FiltersRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.groupMode = false;
        this.layoutId = C1178R.layout.radiobutton_one_title;
        init(context, attrs);
    }

    public FiltersRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.groupMode = false;
        this.layoutId = C1178R.layout.radiobutton_one_title;
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        String titleText = null;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C1178R.styleable.FiltersRadioButton, 0, 0);
            titleText = a.getText(0).toString();
            this.layoutId = a.getResourceId(1, C1178R.layout.radiobutton_one_title);
            a.recycle();
        }
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(this.layoutId, this, true);
        this.radioButton = (RadioButton) findViewById(C1178R.id.radio_btn);
        this.title = (TextView) findViewById(C1759R.id.title);
        this.subTitle = (TextView) findViewById(C1178R.id.subtitle);
        findViewById(C1178R.id.clickable).setOnClickListener(new C12881());
        TextView title = (TextView) findViewById(C1759R.id.title);
        if (titleText != null) {
            title.setText(titleText);
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public void setGroupMode(boolean groupMode) {
        this.groupMode = groupMode;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setSubtitle(@StringRes int subtitle) {
        if (this.subTitle != null) {
            this.subTitle.setText(subtitle);
        }
    }

    public boolean isChecked() {
        return this.radioButton.isChecked();
    }

    public void setChecked(boolean checked) {
        this.title.setSelected(checked);
        if (this.subTitle != null) {
            this.subTitle.setSelected(checked);
        }
        this.radioButton.setChecked(checked);
    }
}
