package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelFragment$$Lambda$11 implements Runnable {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$11(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static Runnable lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$11(hotelFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onBookingLoadFailed$8();
    }
}
