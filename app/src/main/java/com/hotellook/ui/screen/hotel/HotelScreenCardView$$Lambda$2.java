package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelScreenCardView$$Lambda$2 implements Runnable {
    private final HotelScreenCardView arg$1;

    private HotelScreenCardView$$Lambda$2(HotelScreenCardView hotelScreenCardView) {
        this.arg$1 = hotelScreenCardView;
    }

    public static Runnable lambdaFactory$(HotelScreenCardView hotelScreenCardView) {
        return new HotelScreenCardView$$Lambda$2(hotelScreenCardView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$informAboutInitialPhotoSelection$1();
    }
}
