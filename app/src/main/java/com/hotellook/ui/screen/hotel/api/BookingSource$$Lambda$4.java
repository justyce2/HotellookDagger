package com.hotellook.ui.screen.hotel.api;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class BookingSource$$Lambda$4 implements Action0 {
    private final BookingSource arg$1;

    private BookingSource$$Lambda$4(BookingSource bookingSource) {
        this.arg$1 = bookingSource;
    }

    public static Action0 lambdaFactory$(BookingSource bookingSource) {
        return new BookingSource$$Lambda$4(bookingSource);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$observe$2();
    }
}
