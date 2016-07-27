package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelFragment$$Lambda$3 implements Action1 {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$3(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static Action1 lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$3(hotelFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToBasicData$1((Throwable) obj);
    }
}
