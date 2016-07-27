package com.hotellook.ui.screen.hotel.api;

import com.hotellook.core.api.pojo.deeplink.DeeplinkData;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class BookingSource$$Lambda$1 implements Func1 {
    private final Offer arg$1;
    private final SearchParams arg$2;
    private final long arg$3;

    private BookingSource$$Lambda$1(Offer offer, SearchParams searchParams, long j) {
        this.arg$1 = offer;
        this.arg$2 = searchParams;
        this.arg$3 = j;
    }

    public static Func1 lambdaFactory$(Offer offer, SearchParams searchParams, long j) {
        return new BookingSource$$Lambda$1(offer, searchParams, j);
    }

    @Hidden
    public Object call(Object obj) {
        return new Booking((DeeplinkData) obj, this.arg$1, this.arg$2, this.arg$3);
    }
}
