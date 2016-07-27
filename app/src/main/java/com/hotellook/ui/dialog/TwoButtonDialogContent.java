package com.hotellook.ui.dialog;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.view.View.OnClickListener;

public class TwoButtonDialogContent {
    public final OnClickListener firstBtnClickListener;
    public Integer firstBtnColor;
    public final int firstBtnTxtId;
    public final int message;
    public final OnClickListener secondBtnClickListener;
    public final int secondBtnTxtId;
    public final int title;

    public static final class Builder {
        private OnClickListener firstBtnClickListener;
        private Integer firstBtnColor;
        private int firstBtnTxtId;
        private int message;
        private OnClickListener secondBtnClickListener;
        private int secondBtnTxtId;
        private int title;

        private Builder() {
        }

        public Builder title(@StringRes int val) {
            this.title = val;
            return this;
        }

        public Builder message(@StringRes int val) {
            this.message = val;
            return this;
        }

        public Builder firstBtnTxtId(@StringRes int val) {
            this.firstBtnTxtId = val;
            return this;
        }

        public Builder secondBtnTxtId(@StringRes int val) {
            this.secondBtnTxtId = val;
            return this;
        }

        public Builder firstBtnClickListener(OnClickListener val) {
            this.firstBtnClickListener = val;
            return this;
        }

        public Builder secondBtnClickListener(OnClickListener val) {
            this.secondBtnClickListener = val;
            return this;
        }

        public Builder firstBtnColor(@ColorRes int val) {
            this.firstBtnColor = Integer.valueOf(val);
            return this;
        }

        public TwoButtonDialogContent build() {
            return new TwoButtonDialogContent(this.title, this.message, this.firstBtnTxtId, this.secondBtnTxtId, this.firstBtnClickListener, this.secondBtnClickListener, this.firstBtnColor);
        }
    }

    public TwoButtonDialogContent(int titleId, int messageId, int firstBtnTxtId, int secondBtnTxtId, OnClickListener firstBtnClickListener, OnClickListener secondBtnClickListener) {
        this.title = titleId;
        this.message = messageId;
        this.firstBtnTxtId = firstBtnTxtId;
        this.secondBtnTxtId = secondBtnTxtId;
        this.firstBtnClickListener = firstBtnClickListener;
        this.secondBtnClickListener = secondBtnClickListener;
    }

    public TwoButtonDialogContent(int titleId, int messageId, int firstBtnTxtId, int secondBtnTxtId, OnClickListener firstBtnClickListener, OnClickListener secondBtnClickListener, Integer firstBtnColor) {
        this(titleId, messageId, firstBtnTxtId, secondBtnTxtId, firstBtnClickListener, secondBtnClickListener);
        this.firstBtnColor = firstBtnColor;
    }

    public static Builder newBuilder() {
        return new Builder();
    }
}
