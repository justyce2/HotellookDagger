package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class DumpSource$$Lambda$1 implements Func1 {
    private final DumpSource arg$1;
    private final String arg$2;

    private DumpSource$$Lambda$1(DumpSource dumpSource, String str) {
        this.arg$1 = dumpSource;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(DumpSource dumpSource, String str) {
        return new DumpSource$$Lambda$1(dumpSource, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$observe$0(this.arg$2, (List) obj);
    }
}
