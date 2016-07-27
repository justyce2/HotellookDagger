package com.hotellook.ui.screen.hotel.data;

import com.hotellook.search.HighlightsByRooms;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class HotelDataSource$$Lambda$22 implements Action1 {
    private final HotelDataSource arg$1;

    private HotelDataSource$$Lambda$22(HotelDataSource hotelDataSource) {
        this.arg$1 = hotelDataSource;
    }

    public static Action1 lambdaFactory$(HotelDataSource hotelDataSource) {
        return new HotelDataSource$$Lambda$22(hotelDataSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$createOffersObservable$19((HighlightsByRooms) obj);
    }
}
