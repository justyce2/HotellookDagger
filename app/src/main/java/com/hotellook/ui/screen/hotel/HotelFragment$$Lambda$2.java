package com.hotellook.ui.screen.hotel;

import android.util.Pair;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelFragment$$Lambda$2 implements Action1 {
    private final HotelFragment arg$1;

    private HotelFragment$$Lambda$2(HotelFragment hotelFragment) {
        this.arg$1 = hotelFragment;
    }

    public static Action1 lambdaFactory$(HotelFragment hotelFragment) {
        return new HotelFragment$$Lambda$2(hotelFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToBasicData$0((Pair) obj);
    }
}
