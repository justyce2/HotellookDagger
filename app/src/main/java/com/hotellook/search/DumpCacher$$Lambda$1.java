package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DumpCacher$$Lambda$1 implements Action1 {
    private final DumpCacher arg$1;

    private DumpCacher$$Lambda$1(DumpCacher dumpCacher) {
        this.arg$1 = dumpCacher;
    }

    public static Action1 lambdaFactory$(DumpCacher dumpCacher) {
        return new DumpCacher$$Lambda$1(dumpCacher);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$cache$0((Throwable) obj);
    }
}
