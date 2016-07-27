package com.hotellook.ui.screen.hotel;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class OffersLoaderStateModel$$Lambda$2 implements Action1 {
    private final OffersLoaderStateModel arg$1;

    private OffersLoaderStateModel$$Lambda$2(OffersLoaderStateModel offersLoaderStateModel) {
        this.arg$1 = offersLoaderStateModel;
    }

    public static Action1 lambdaFactory$(OffersLoaderStateModel offersLoaderStateModel) {
        return new OffersLoaderStateModel$$Lambda$2(offersLoaderStateModel);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeOffers$1((List) obj);
    }
}
