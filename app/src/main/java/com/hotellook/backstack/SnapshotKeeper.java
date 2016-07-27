package com.hotellook.backstack;

import android.support.annotation.Nullable;

public final class SnapshotKeeper {
    private Object snapshot;

    public void push(@Nullable Object snapshot) {
        this.snapshot = snapshot;
    }

    @Nullable
    public Object pop() {
        Object snapshot = this.snapshot;
        this.snapshot = null;
        return snapshot;
    }
}
