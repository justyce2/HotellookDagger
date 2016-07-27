package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.prices.OffersLoaderState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class OffersLoaderStateModel$$Lambda$1 implements Action1 {
    private final OffersLoaderStateModel arg$1;

    private OffersLoaderStateModel$$Lambda$1(OffersLoaderStateModel offersLoaderStateModel) {
        this.arg$1 = offersLoaderStateModel;
    }

    public static Action1 lambdaFactory$(OffersLoaderStateModel offersLoaderStateModel) {
        return new OffersLoaderStateModel$$Lambda$1(offersLoaderStateModel);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((OffersLoaderState) obj);
    }
}
