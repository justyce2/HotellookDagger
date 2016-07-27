package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelFragment$$Lambda$5 implements Runnable {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$5(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static Runnable lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$5(hotelFragment);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$restoreScrollProgress$3();
    }
}
