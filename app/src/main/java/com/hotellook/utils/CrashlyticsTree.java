package com.hotellook.utils;

import com.crashlytics.android.Crashlytics;
import timber.log.Timber.Tree;

public final class CrashlyticsTree extends Tree {
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority != 2 && priority != 3) {
            Crashlytics.log(priority, tag, message);
            if (t != null && priority == 6) {
                Crashlytics.logException(t);
            }
        }
    }
}
