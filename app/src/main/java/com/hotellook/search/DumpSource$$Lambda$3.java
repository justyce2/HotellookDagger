package com.hotellook.search;

import android.util.Pair;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func2;

final /* synthetic */ class DumpSource$$Lambda$3 implements Func2 {
    private static final DumpSource$$Lambda$3 instance;

    static {
        instance = new DumpSource$$Lambda$3();
    }

    private DumpSource$$Lambda$3() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return new Pair((Integer) obj, (List) obj2);
    }
}
