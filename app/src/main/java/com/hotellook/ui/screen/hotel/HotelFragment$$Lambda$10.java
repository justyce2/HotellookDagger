package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelFragment$$Lambda$10 implements Action1 {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$10(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static Action1 lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$10(hotelFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToBooking$7((Throwable) obj);
    }
}
