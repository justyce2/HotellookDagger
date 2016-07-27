package com.hotellook.search;

import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class SearchEngine$$Lambda$3 implements Func1 {
    private static final SearchEngine$$Lambda$3 instance;

    static {
        instance = new SearchEngine$$Lambda$3();
    }

    private SearchEngine$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new OffersSearchLaunchData(((SearchId) obj).gates(), ((SearchId) obj).gatesToShowUser(), ((SearchId) obj).searchId());
    }
}
