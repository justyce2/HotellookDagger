package com.hotellook.ui.view.kidspicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.common.view.MonkeySafeClickListener;

public class KidsPickerItemView extends RelativeLayout {
    private int mAge;
    private View mBtnRemove;
    private OnClickListener mOnRemoveListener;
    private TextView mTvAge;
    private TextView mTvDefault;

    /* renamed from: com.hotellook.ui.view.kidspicker.KidsPickerItemView.1 */
    class C14311 extends MonkeySafeClickListener {
        C14311() {
        }

        public void onSafeClick(View v) {
            if (mOnRemoveListener != null) {
                mOnRemoveListener.onClick(KidsPickerItemView.this);
            }
        }
    }

    public KidsPickerItemView(Context context) {
        super(context);
    }

    public KidsPickerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mBtnRemove = findViewById(C1178R.id.remove);
        this.mTvAge = (TextView) findViewById(C1178R.id.tv_kid_age);
        this.mTvDefault = (TextView) findViewById(C1178R.id.tv_default);
        this.mBtnRemove.setOnClickListener(new C14311());
        this.mBtnRemove.setAlpha(1.0f);
        this.mTvAge.setAlpha(1.0f);
        this.mTvDefault.setAlpha(0.0f);
    }

    public void setEmpty(boolean isEmpty) {
        if (isEmpty) {
            this.mBtnRemove.animate().alpha(0.0f);
            this.mTvAge.animate().alpha(0.0f);
            this.mTvDefault.animate().alpha(1.0f);
            return;
        }
        this.mBtnRemove.animate().alpha(1.0f);
        this.mTvAge.animate().alpha(1.0f);
        this.mTvDefault.animate().alpha(0.0f);
    }

    public Integer getAge() {
        return this.mAge;
    }

    public void setAge(Integer age) {
        this.mAge = age;
        this.mTvAge.setText(KidsPickerView.getKidAgeText(age, getContext()));
    }

    public void setOnRemoveListener(OnClickListener onRemoveListener) {
        this.mOnRemoveListener = onRemoveListener;
    }
}
