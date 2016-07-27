package com.hotellook.ui.screen.browser;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BrowserFragment$$Lambda$1 implements Runnable {
    private final BrowserFragment arg$1;

    private BrowserFragment$$Lambda$1(BrowserFragment browserFragment) {
        this.arg$1 = browserFragment;
    }

    public static Runnable lambdaFactory$(BrowserFragment browserFragment) {
        return new BrowserFragment$$Lambda$1(browserFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onCreateView$0();
    }
}
