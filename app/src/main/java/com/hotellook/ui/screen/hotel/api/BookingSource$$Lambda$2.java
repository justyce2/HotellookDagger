package com.hotellook.ui.screen.hotel.api;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class BookingSource$$Lambda$2 implements Action1 {
    private final BookingSource arg$1;

    private BookingSource$$Lambda$2(BookingSource bookingSource) {
        this.arg$1 = bookingSource;
    }

    public static Action1 lambdaFactory$(BookingSource bookingSource) {
        return new BookingSource$$Lambda$2(bookingSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logLoadedResult((Booking) obj);
    }
}
