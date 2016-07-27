package com.hotellook.ui.view.viewmovers;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SyncScrollerDelegate$$Lambda$1 implements Runnable {
    private final SyncScrollerDelegate arg$1;

    private SyncScrollerDelegate$$Lambda$1(SyncScrollerDelegate syncScrollerDelegate) {
        this.arg$1 = syncScrollerDelegate;
    }

    public static Runnable lambdaFactory$(SyncScrollerDelegate syncScrollerDelegate) {
        return new SyncScrollerDelegate$$Lambda$1(syncScrollerDelegate);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$animateDelayed$0();
    }
}
