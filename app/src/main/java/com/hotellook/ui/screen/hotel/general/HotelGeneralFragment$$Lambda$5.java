package com.hotellook.ui.screen.hotel.general;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;
import timber.log.Timber;

final /* synthetic */ class HotelGeneralFragment$$Lambda$5 implements Action1 {
    private static final HotelGeneralFragment$$Lambda$5 instance;

    static {
        instance = new HotelGeneralFragment$$Lambda$5();
    }

    private HotelGeneralFragment$$Lambda$5() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        Timber.m757w((Throwable) obj, "Unable to load basic data", new Object[0]);
    }
}
