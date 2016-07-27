package com.hotellook.ui.screen.hotel.general;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class HotelGeneralFragment$$Lambda$2 implements Action1 {
    private static final HotelGeneralFragment$$Lambda$2 instance;

    static {
        instance = new HotelGeneralFragment$$Lambda$2();
    }

    private HotelGeneralFragment$$Lambda$2() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "All hotel data failed loading in hotel general", new Object[0]);
    }
}
