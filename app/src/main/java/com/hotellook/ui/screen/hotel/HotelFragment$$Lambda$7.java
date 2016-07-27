package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.api.Booking;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class HotelFragment$$Lambda$7 implements Func2 {
    private static final HotelFragment$$Lambda$7 instance;

    static {
        instance = new HotelFragment$$Lambda$7();
    }

    private HotelFragment$$Lambda$7() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return HotelFragment.lambda$onBookRequest$5((Booking) obj, (Long) obj2);
    }
}
