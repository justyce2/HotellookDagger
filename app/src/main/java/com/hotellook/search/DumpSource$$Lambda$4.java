package com.hotellook.search;

import android.util.Pair;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DumpSource$$Lambda$4 implements Func1 {
    private final DumpSource arg$1;
    private final String arg$2;

    private DumpSource$$Lambda$4(DumpSource dumpSource, String str) {
        this.arg$1 = dumpSource;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(DumpSource dumpSource, String str) {
        return new DumpSource$$Lambda$4(dumpSource, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observe$1(this.arg$2, (Pair) obj);
    }
}
