package com.hotellook.utils;

import android.support.annotation.Nullable;
import rx.Subscription;

public class RxUtil {
    public static void safeUnsubscribe(@Nullable Subscription subscription) {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
