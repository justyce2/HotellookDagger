package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelInfoLoadableLayout$$Lambda$4 implements Action1 {
    private final HotelInfoLoadableLayout arg$1;

    private HotelInfoLoadableLayout$$Lambda$4(HotelInfoLoadableLayout hotelInfoLoadableLayout) {
        this.arg$1 = hotelInfoLoadableLayout;
    }

    public static Action1 lambdaFactory$(HotelInfoLoadableLayout hotelInfoLoadableLayout) {
        return new HotelInfoLoadableLayout$$Lambda$4(hotelInfoLoadableLayout);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeState$3((LCE) obj);
    }
}
