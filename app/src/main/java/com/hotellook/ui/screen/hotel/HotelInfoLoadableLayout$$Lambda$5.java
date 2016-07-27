package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelInfoLoadableLayout$$Lambda$5 implements Action1 {
    private final HotelInfoLoadableLayout arg$1;

    private HotelInfoLoadableLayout$$Lambda$5(HotelInfoLoadableLayout hotelInfoLoadableLayout) {
        this.arg$1 = hotelInfoLoadableLayout;
    }

    public static Action1 lambdaFactory$(HotelInfoLoadableLayout hotelInfoLoadableLayout) {
        return new HotelInfoLoadableLayout$$Lambda$5(hotelInfoLoadableLayout);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeState$4((Throwable) obj);
    }
}
