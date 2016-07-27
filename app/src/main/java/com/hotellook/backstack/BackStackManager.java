package com.hotellook.backstack;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import com.hotellook.utils.Preconditions;
import java.util.LinkedList;

public final class BackStackManager {
    private static final int FIRST_INDEX = 0;
    private static final int UNDEFINED_INDEX = -1;
    private final LinkedList<BackStack> backStacks;

    public BackStackManager() {
        this.backStacks = new LinkedList();
    }

    public void push(int hostId, @NonNull BackStackEntry entry) {
        BackStack backStack = peekBackStack(hostId);
        if (backStack == null) {
            backStack = new BackStack(hostId);
            this.backStacks.push(backStack);
        }
        backStack.push(entry);
    }

    @Nullable
    public BackStackEntry pop(int hostId) {
        BackStack backStack = peekBackStack(hostId);
        if (backStack == null) {
            return null;
        }
        return popFrom(backStack);
    }

    @Nullable
    public Pair<Integer, BackStackEntry> pop() {
        BackStack backStack = peekBackStack();
        if (backStack == null) {
            return null;
        }
        return Pair.create(Integer.valueOf(backStack.hostId), Preconditions.checkNotNull(popFrom(backStack)));
    }

    @NonNull
    private BackStackEntry popFrom(@NonNull BackStack backStack) {
        BackStackEntry entry = (BackStackEntry) Preconditions.checkNotNull(backStack.pop());
        if (backStack.empty()) {
            this.backStacks.remove(backStack);
        }
        return entry;
    }

    public boolean clear(int hostId) {
        BackStack backStack = getBackStack(hostId);
        if (backStack == null) {
            return false;
        }
        this.backStacks.remove(backStack);
        return true;
    }

    public boolean resetToRoot(int hostId, @NonNull String originalRootFragmentName) {
        BackStack backStack = getBackStack(hostId);
        if (backStack == null) {
            return false;
        }
        resetToRoot(backStack, originalRootFragmentName);
        return true;
    }

    private void resetToRoot(@NonNull BackStack backStack, @NonNull String originalRootFragmentName) {
        BackStackEntry entry;
        do {
            entry = (BackStackEntry) Preconditions.checkNotNull(backStack.pop());
        } while (!backStack.empty());
        if (!entry.fname.equals(originalRootFragmentName)) {
            entry = new BackStackEntry(originalRootFragmentName);
        }
        backStack.push(entry);
    }

    @Nullable
    private BackStack peekBackStack() {
        return (BackStack) this.backStacks.peek();
    }

    @Nullable
    private BackStack peekBackStack(int hostId) {
        int index = indexOfBackStack(hostId);
        if (index == UNDEFINED_INDEX) {
            return null;
        }
        BackStack backStack = (BackStack) this.backStacks.get(index);
        if (index == 0) {
            return backStack;
        }
        this.backStacks.remove(index);
        this.backStacks.push(backStack);
        return backStack;
    }

    @Nullable
    private BackStack getBackStack(int hostId) {
        int index = indexOfBackStack(hostId);
        if (index == UNDEFINED_INDEX) {
            return null;
        }
        return (BackStack) this.backStacks.get(index);
    }

    private int indexOfBackStack(int hostId) {
        int size = this.backStacks.size();
        for (int i = FIRST_INDEX; i < size; i++) {
            if (((BackStack) this.backStacks.get(i)).hostId == hostId) {
                return i;
            }
        }
        return UNDEFINED_INDEX;
    }
}
