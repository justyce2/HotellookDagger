package com.hotellook.ui.screen.hotel.general;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelGeneralFragment$$Lambda$1 implements Action1 {
    private final HotelGeneralFragment arg$1;

    private HotelGeneralFragment$$Lambda$1(HotelGeneralFragment hotelGeneralFragment) {
        this.arg$1 = hotelGeneralFragment;
    }

    public static Action1 lambdaFactory$(HotelGeneralFragment hotelGeneralFragment) {
        return new HotelGeneralFragment$$Lambda$1(hotelGeneralFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToLoadableData$0((Boolean) obj);
    }
}
