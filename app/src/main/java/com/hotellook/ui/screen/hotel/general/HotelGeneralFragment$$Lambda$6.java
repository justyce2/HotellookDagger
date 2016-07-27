package com.hotellook.ui.screen.hotel.general;

import android.view.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelGeneralFragment$$Lambda$6 implements Runnable {
    private final HotelGeneralFragment arg$1;
    private final View arg$2;

    private HotelGeneralFragment$$Lambda$6(HotelGeneralFragment hotelGeneralFragment, View view) {
        this.arg$1 = hotelGeneralFragment;
        this.arg$2 = view;
    }

    public static Runnable lambdaFactory$(HotelGeneralFragment hotelGeneralFragment, View view) {
        return new HotelGeneralFragment$$Lambda$6(hotelGeneralFragment, view);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$postSetScrollAction$4(this.arg$2);
    }
}
