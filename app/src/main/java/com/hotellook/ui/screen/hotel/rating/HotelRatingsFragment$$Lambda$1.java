package com.hotellook.ui.screen.hotel.rating;

import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelRatingsFragment$$Lambda$1 implements Action1 {
    private final HotelRatingsFragment arg$1;

    private HotelRatingsFragment$$Lambda$1(HotelRatingsFragment hotelRatingsFragment) {
        this.arg$1 = hotelRatingsFragment;
    }

    public static Action1 lambdaFactory$(HotelRatingsFragment hotelRatingsFragment) {
        return new HotelRatingsFragment$$Lambda$1(hotelRatingsFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToLoadableLayoutStates$0((LCE) obj);
    }
}
