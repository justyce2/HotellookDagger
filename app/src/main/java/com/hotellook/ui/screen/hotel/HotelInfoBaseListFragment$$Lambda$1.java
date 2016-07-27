package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelInfoBaseListFragment$$Lambda$1 implements Action1 {
    private final HotelInfoBaseListFragment arg$1;

    private HotelInfoBaseListFragment$$Lambda$1(HotelInfoBaseListFragment hotelInfoBaseListFragment) {
        this.arg$1 = hotelInfoBaseListFragment;
    }

    public static Action1 lambdaFactory$(HotelInfoBaseListFragment hotelInfoBaseListFragment) {
        return new HotelInfoBaseListFragment$$Lambda$1(hotelInfoBaseListFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$subscribeToLoadableLayoutStates$0((LCE) obj);
    }
}
