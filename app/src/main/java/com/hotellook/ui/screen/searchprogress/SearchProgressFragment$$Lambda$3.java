package com.hotellook.ui.screen.searchprogress;

import com.hotellook.search.OffersSearchLaunchData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class SearchProgressFragment$$Lambda$3 implements Func1 {
    private static final SearchProgressFragment$$Lambda$3 instance;

    static {
        instance = new SearchProgressFragment$$Lambda$3();
    }

    private SearchProgressFragment$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((OffersSearchLaunchData) obj).gatesToShowUser();
    }
}
