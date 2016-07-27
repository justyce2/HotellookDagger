package com.hotellook.ui.view.viewmovers;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class AsyncScrollerDelegate$$Lambda$1 implements Runnable {
    private final AsyncScrollerDelegate arg$1;

    private AsyncScrollerDelegate$$Lambda$1(AsyncScrollerDelegate asyncScrollerDelegate) {
        this.arg$1 = asyncScrollerDelegate;
    }

    public static Runnable lambdaFactory$(AsyncScrollerDelegate asyncScrollerDelegate) {
        return new AsyncScrollerDelegate$$Lambda$1(asyncScrollerDelegate);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$animateDelayed$0();
    }
}
