package com.hotellook;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotellookApplication$$Lambda$2 implements UncaughtExceptionHandler {
    private final HotellookApplication arg$1;
    private final UncaughtExceptionHandler arg$2;

    private HotellookApplication$$Lambda$2(HotellookApplication hotellookApplication, UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.arg$1 = hotellookApplication;
        this.arg$2 = uncaughtExceptionHandler;
    }

    public static UncaughtExceptionHandler lambdaFactory$(HotellookApplication hotellookApplication, UncaughtExceptionHandler uncaughtExceptionHandler) {
        return new HotellookApplication$$Lambda$2(hotellookApplication, uncaughtExceptionHandler);
    }

    @Hidden
    public void uncaughtException(Thread thread, Throwable th) {
        this.arg$1.lambda$setDefaultUncaughtExceptionHandler$1(this.arg$2, thread, th);
    }
}
