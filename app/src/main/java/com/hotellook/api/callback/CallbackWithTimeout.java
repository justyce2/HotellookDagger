package com.hotellook.api.callback;

import android.os.Handler;
import android.os.Looper;

public abstract class CallbackWithTimeout<T> extends CancelableRetrofitCallback<T> {
    protected boolean canceled;
    private Handler handler;
    private Runnable timeoutAction;

    /* renamed from: com.hotellook.api.callback.CallbackWithTimeout.1 */
    class C11811 implements Runnable {
        C11811() {
        }

        public void run() {
            if (!CallbackWithTimeout.this.canceled && !CallbackWithTimeout.this.finished) {
                CallbackWithTimeout.this.handler.removeCallbacks(CallbackWithTimeout.this.timeoutAction);
                CallbackWithTimeout.this.failure(null);
            }
        }
    }

    protected CallbackWithTimeout(long timeout) {
        this.canceled = false;
        this.timeoutAction = new C11811();
        this.handler = new Handler(Looper.getMainLooper());
        this.handler.postDelayed(this.timeoutAction, timeout);
    }

    public void onCancel() {
        super.onCancel();
        this.handler.removeCallbacks(this.timeoutAction);
    }
}
