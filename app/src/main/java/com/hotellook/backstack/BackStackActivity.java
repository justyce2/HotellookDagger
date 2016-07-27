package com.hotellook.backstack;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import timber.log.Timber;

public abstract class BackStackActivity extends AppCompatActivity {
    protected BackStackManager backStackManager;

    protected boolean pushFragmentToBackStack(int hostId, @NonNull Fragment fragment) {
        try {
            this.backStackManager.push(hostId, BackStackEntry.create(getSupportFragmentManager(), fragment));
            return true;
        } catch (Exception e) {
            Timber.m757w(e, "Failed to add fragment to back stack", new Object[0]);
            return false;
        }
    }

    @Nullable
    protected Fragment popFragmentFromBackStack(int hostId) {
        BackStackEntry entry = this.backStackManager.pop(hostId);
        return entry != null ? entry.toFragment(this) : null;
    }

    @Nullable
    protected Pair<Integer, Fragment> popFragmentFromBackStack() {
        Pair<Integer, BackStackEntry> pair = this.backStackManager.pop();
        return pair != null ? Pair.create(pair.first, ((BackStackEntry) pair.second).toFragment(this)) : null;
    }

    protected boolean resetBackStackToRoot(@IdRes int hostId, @NonNull Fragment originalRootFragment) {
        return this.backStackManager.resetToRoot(hostId, originalRootFragment.getClass().getName());
    }

    protected boolean clearBackStack(@IdRes int hostId) {
        return this.backStackManager.clear(hostId);
    }
}
