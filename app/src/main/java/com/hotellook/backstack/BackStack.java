package com.hotellook.backstack;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.LinkedList;

final class BackStack {
    private final LinkedList<BackStackEntry> entries;
    final int hostId;

    public BackStack(int hostId) {
        this.entries = new LinkedList();
        this.hostId = hostId;
    }

    public void push(@NonNull BackStackEntry entry) {
        this.entries.push(entry);
    }

    @Nullable
    public BackStackEntry pop() {
        return (BackStackEntry) this.entries.pop();
    }

    public boolean empty() {
        return this.entries.isEmpty();
    }
}
