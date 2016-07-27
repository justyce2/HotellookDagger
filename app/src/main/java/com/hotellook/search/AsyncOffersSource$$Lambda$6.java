package com.hotellook.search;

import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func3;

final /* synthetic */ class AsyncOffersSource$$Lambda$6 implements Func3 {
    private static final AsyncOffersSource$$Lambda$6 instance;

    static {
        instance = new AsyncOffersSource$$Lambda$6();
    }

    private AsyncOffersSource$$Lambda$6() {
    }

    public static Func3 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2, Object obj3) {
        return new SearchLaunchData((SearchId) obj, (List) obj3);
    }
}
