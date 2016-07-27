package com.hotellook.common.view;

import android.view.View;
import android.view.View.OnClickListener;
import com.hotellook.utils.AndroidUtils;
import timber.log.Timber;

public abstract class MonkeySafeClickListener implements OnClickListener {
    public abstract void onSafeClick(View view);

    public void onClick(View v) {
        if (AndroidUtils.preventDoubleClick()) {
            Timber.m751d("Click prevented", new Object[0]);
        } else {
            onSafeClick(v);
        }
    }
}
