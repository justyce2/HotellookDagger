package com.hotellook.ui.view.viewmovers;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SyncScrollerDelegate$$Lambda$2 implements Runnable {
    private final SyncScrollerDelegate arg$1;

    private SyncScrollerDelegate$$Lambda$2(SyncScrollerDelegate syncScrollerDelegate) {
        this.arg$1 = syncScrollerDelegate;
    }

    public static Runnable lambdaFactory$(SyncScrollerDelegate syncScrollerDelegate) {
        return new SyncScrollerDelegate$$Lambda$2(syncScrollerDelegate);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onTouchFinished$1();
    }
}
