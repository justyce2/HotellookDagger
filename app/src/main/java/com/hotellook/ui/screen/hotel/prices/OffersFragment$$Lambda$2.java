package com.hotellook.ui.screen.hotel.prices;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class OffersFragment$$Lambda$2 implements Action1 {
    private static final OffersFragment$$Lambda$2 instance;

    static {
        instance = new OffersFragment$$Lambda$2();
    }

    private OffersFragment$$Lambda$2() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "Error while observing offers state", new Object[0]);
    }
}
