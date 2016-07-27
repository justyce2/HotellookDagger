package com.hotellook.backstack;

import android.support.annotation.Nullable;

public interface SavableFragment {
    void setInitialSnapshot(@Nullable Object obj);

    @Nullable
    Object takeSnapshot();
}
