package com.hotellook.ui.screen.hotel.data;

import com.hotellook.search.DiscountsByRooms;
import com.hotellook.search.HighlightsByRooms;
import com.hotellook.search.RoomTypes;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func4;

final /* synthetic */ class HotelDataSource$$Lambda$23 implements Func4 {
    private static final HotelDataSource$$Lambda$23 instance;

    static {
        instance = new HotelDataSource$$Lambda$23();
    }

    private HotelDataSource$$Lambda$23() {
    }

    public static Func4 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2, Object obj3, Object obj4) {
        return HotelDataSource.lambda$createOffersObservable$20((RoomTypes) obj, (List) obj2, (DiscountsByRooms) obj3, (HighlightsByRooms) obj4);
    }
}
