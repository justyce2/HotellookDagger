package com.hotellook.ui.screen.hotel.general;

import android.util.Pair;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelGeneralFragment$$Lambda$4 implements Action1 {
    private final HotelGeneralFragment arg$1;

    private HotelGeneralFragment$$Lambda$4(HotelGeneralFragment hotelGeneralFragment) {
        this.arg$1 = hotelGeneralFragment;
    }

    public static Action1 lambdaFactory$(HotelGeneralFragment hotelGeneralFragment) {
        return new HotelGeneralFragment$$Lambda$4(hotelGeneralFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToBasicData$2((Pair) obj);
    }
}
