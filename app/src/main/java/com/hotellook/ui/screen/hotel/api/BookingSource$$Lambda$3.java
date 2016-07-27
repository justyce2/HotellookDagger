package com.hotellook.ui.screen.hotel.api;

import com.hotellook.core.api.pojo.search.Offer;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class BookingSource$$Lambda$3 implements Action1 {
    private final BookingSource arg$1;
    private final long arg$2;
    private final Offer arg$3;

    private BookingSource$$Lambda$3(BookingSource bookingSource, long j, Offer offer) {
        this.arg$1 = bookingSource;
        this.arg$2 = j;
        this.arg$3 = offer;
    }

    public static Action1 lambdaFactory$(BookingSource bookingSource, long j, Offer offer) {
        return new BookingSource$$Lambda$3(bookingSource, j, offer);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observe$1(this.arg$2, this.arg$3, (Throwable) obj);
    }
}
