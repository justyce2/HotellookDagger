package com.hotellook.backstack;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager;

final class BackStackEntry {
    final Bundle args;
    final String fname;
    final Object snapshot;
    final SavedState state;

    public BackStackEntry(@NonNull String fname) {
        this(fname, null, null, null);
    }

    public BackStackEntry(@NonNull String fname, @Nullable Bundle args, @Nullable SavedState state, @Nullable Object snapshot) {
        this.args = args;
        this.fname = fname;
        this.state = state;
        this.snapshot = snapshot;
    }

    @NonNull
    public static BackStackEntry create(@NonNull FragmentManager fm, @NonNull Fragment f) {
        Bundle args = f.getArguments();
        String fname = f.getClass().getName();
        SavedState state = fm.saveFragmentInstanceState(f);
        Object snapshot = null;
        if (f instanceof SavableFragment) {
            snapshot = ((SavableFragment) f).takeSnapshot();
        }
        return new BackStackEntry(fname, args, state, snapshot);
    }

    @NonNull
    public Fragment toFragment(@NonNull Context context) {
        Fragment f = Fragment.instantiate(context, this.fname);
        f.setArguments(this.args);
        f.setInitialSavedState(this.state);
        if (f instanceof SavableFragment) {
            ((SavableFragment) f).setInitialSnapshot(this.snapshot);
        }
        return f;
    }
}
