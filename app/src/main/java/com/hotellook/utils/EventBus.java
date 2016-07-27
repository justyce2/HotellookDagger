package com.hotellook.utils;

import android.os.Handler;
import android.os.Looper;
import com.squareup.otto.Bus;
import timber.log.Timber;

public class EventBus extends Bus {
    private final Handler mainThread;

    public EventBus() {
        this.mainThread = new Handler(Looper.getMainLooper());
    }

    public void register(Object object) {
        try {
            super.register(object);
        } catch (Exception e) {
            Timber.m751d("already registered %s", object);
        }
    }

    public void unregister(Object object) {
        try {
            super.unregister(object);
        } catch (Exception e) {
            Timber.m751d("not registered %s", object);
        }
    }

    public void post(Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            this.mainThread.post(EventBus$$Lambda$1.lambdaFactory$(this, event));
        }
        Timber.m751d("Posted event: %s", event.getClass().getSimpleName());
    }

    /* synthetic */ void lambda$post$0(Object event) {
        super.post(event);
    }
}
