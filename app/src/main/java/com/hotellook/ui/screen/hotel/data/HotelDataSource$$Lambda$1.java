package com.hotellook.ui.screen.hotel.data;

import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.core.api.pojo.search.SearchId;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$1 implements Func1 {
    private final List arg$1;

    private HotelDataSource$$Lambda$1(List list) {
        this.arg$1 = list;
    }

    public static Func1 lambdaFactory$(List list) {
        return new HotelDataSource$$Lambda$1(list);
    }

    @Hidden
    public Object call(Object obj) {
        return new SearchInfoForBooking(this.arg$1, ((SearchId) obj).searchId());
    }
}
