package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.data.BasicHotelData;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.concurrent.Callable;

final /* synthetic */ class HotelFragment$$Lambda$4 implements Callable {
    private final HotelFragment arg$1;
    private final BasicHotelData arg$2;

    private HotelFragment$$Lambda$4(HotelFragment hotelFragment, BasicHotelData basicHotelData) {
        this.arg$1 = hotelFragment;
        this.arg$2 = basicHotelData;
    }

    public static Callable lambdaFactory$(HotelFragment hotelFragment, BasicHotelData basicHotelData) {
        return new HotelFragment$$Lambda$4(hotelFragment, basicHotelData);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$startAppIndexingViewing$2(this.arg$2);
    }
}
