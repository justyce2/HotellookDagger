package com.hotellook.ui.screen.hotel;

import com.hotellook.api.RequestFlags;
import com.hotellook.booking.SearchInfoForBooking;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.search.SearchParams;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelFragment$$Lambda$8 implements Func1 {
    private final HotelFragment arg$1;
    private final long arg$2;
    private final SearchParams arg$3;
    private final RequestFlags arg$4;
    private final int arg$5;
    private final Offer arg$6;

    private HotelFragment$$Lambda$8(HotelFragment hotelFragment, long j, SearchParams searchParams, RequestFlags requestFlags, int i, Offer offer) {
        this.arg$1 = hotelFragment;
        this.arg$2 = j;
        this.arg$3 = searchParams;
        this.arg$4 = requestFlags;
        this.arg$5 = i;
        this.arg$6 = offer;
    }

    public static Func1 lambdaFactory$(HotelFragment hotelFragment, long j, SearchParams searchParams, RequestFlags requestFlags, int i, Offer offer) {
        return new HotelFragment$$Lambda$8(hotelFragment, j, searchParams, requestFlags, i, offer);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$createBookingObservable$6(this.arg$2, this.arg$3, this.arg$4, this.arg$5, this.arg$6, (SearchInfoForBooking) obj);
    }
}
