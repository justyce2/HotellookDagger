package com.hotellook.ui.screen.hotel.general;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class BestOfferView$$Lambda$2 implements Action1 {
    private static final BestOfferView$$Lambda$2 instance;

    static {
        instance = new BestOfferView$$Lambda$2();
    }

    private BestOfferView$$Lambda$2() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "Error while observing prices state in best offer widget", new Object[0]);
    }
}
