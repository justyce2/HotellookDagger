package com.hotellook.objectskeeper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface SaveStateObjectKeeper {
    void clean();

    boolean contains(@NonNull String str);

    @Nullable
    Object poll(@NonNull String str);

    void put(@NonNull String str, @NonNull Object obj);

    void remove(@NonNull String str);
}
