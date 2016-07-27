package com.hotellook.ui.screen.hotel.data;

import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.search.Locations;
import com.hotellook.search.OffersSearchLaunchData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class HotelDataSource$$Lambda$4 implements Func2 {
    private static final HotelDataSource$$Lambda$4 instance;

    static {
        instance = new HotelDataSource$$Lambda$4();
    }

    private HotelDataSource$$Lambda$4() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return new SearchInfoForBooking(((Locations) obj2).ids(), ((OffersSearchLaunchData) obj).searchId());
    }
}
