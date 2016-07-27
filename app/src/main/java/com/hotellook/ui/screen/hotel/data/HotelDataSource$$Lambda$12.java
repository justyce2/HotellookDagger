package com.hotellook.ui.screen.hotel.data;

import com.hotellook.search.Locations;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelDataSource$$Lambda$12 implements Func1 {
    private final int arg$1;

    private HotelDataSource$$Lambda$12(int i) {
        this.arg$1 = i;
    }

    public static Func1 lambdaFactory$(int i) {
        return new HotelDataSource$$Lambda$12(i);
    }

    @Hidden
    public Object call(Object obj) {
        return ((Locations) obj).getById(this.arg$1);
    }
}
