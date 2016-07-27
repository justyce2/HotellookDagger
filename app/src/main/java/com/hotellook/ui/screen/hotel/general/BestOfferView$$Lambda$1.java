package com.hotellook.ui.screen.hotel.general;

import com.hotellook.ui.screen.hotel.prices.OffersLoaderState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class BestOfferView$$Lambda$1 implements Action1 {
    private final BestOfferView arg$1;

    private BestOfferView$$Lambda$1(BestOfferView bestOfferView) {
        this.arg$1 = bestOfferView;
    }

    public static Action1 lambdaFactory$(BestOfferView bestOfferView) {
        return new BestOfferView$$Lambda$1(bestOfferView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.setState((OffersLoaderState) obj);
    }
}
