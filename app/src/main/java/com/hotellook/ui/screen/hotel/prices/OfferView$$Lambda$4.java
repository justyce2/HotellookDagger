package com.hotellook.ui.screen.hotel.prices;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class OfferView$$Lambda$4 implements Runnable {
    private final OfferView arg$1;

    private OfferView$$Lambda$4(OfferView offerView) {
        this.arg$1 = offerView;
    }

    public static Runnable lambdaFactory$(OfferView offerView) {
        return new OfferView$$Lambda$4(offerView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$postLoadImage$0();
    }
}
