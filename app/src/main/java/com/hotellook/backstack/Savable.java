package com.hotellook.backstack;

import android.support.annotation.NonNull;

public interface Savable {
    void restoreState(@NonNull Object obj);

    @NonNull
    Object saveState();
}
