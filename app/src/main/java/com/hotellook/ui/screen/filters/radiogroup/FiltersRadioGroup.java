package com.hotellook.ui.screen.filters.radiogroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class FiltersRadioGroup extends LinearLayout implements com.hotellook.ui.screen.filters.radiogroup.FiltersRadioButton.OnCheckedChangeListener {
    private int mChechedId;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private List<FiltersRadioButton> mRadioGroupList;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(FiltersRadioGroup filtersRadioGroup, int i);
    }

    public FiltersRadioGroup(Context context) {
        super(context);
        this.mRadioGroupList = new ArrayList();
    }

    public FiltersRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mRadioGroupList = new ArrayList();
    }

    public FiltersRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRadioGroupList = new ArrayList();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof FiltersRadioButton) {
                this.mRadioGroupList.add((FiltersRadioButton) view);
            }
        }
        for (FiltersRadioButton filtersRadioButton : this.mRadioGroupList) {
            filtersRadioButton.setGroupMode(true);
            filtersRadioButton.setOnCheckedChangeListener(this);
            if (filtersRadioButton.isChecked()) {
                this.mChechedId = filtersRadioButton.getId();
            }
        }
    }

    public void addRadioButton(FiltersRadioButton radioButton) {
        radioButton.setGroupMode(true);
        radioButton.setOnCheckedChangeListener(this);
        this.mRadioGroupList.add(radioButton);
        addView(radioButton);
        if (radioButton.isChecked()) {
            this.mChechedId = radioButton.getId();
        }
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void onCheckedChanged(View view, boolean checked) {
        int id = view.getId();
        if (id != this.mChechedId) {
            this.mChechedId = id;
            if (this.mOnCheckedChangeListener != null) {
                this.mOnCheckedChangeListener.onCheckedChanged(this, id);
            }
            for (FiltersRadioButton filtersRadioButton : this.mRadioGroupList) {
                if (filtersRadioButton.getId() != id) {
                    filtersRadioButton.setChecked(false);
                }
            }
        }
    }

    public void setChecked(int viewId) {
        for (FiltersRadioButton view : this.mRadioGroupList) {
            view.setChecked(view.getId() == viewId);
        }
        this.mChechedId = viewId;
    }
}
