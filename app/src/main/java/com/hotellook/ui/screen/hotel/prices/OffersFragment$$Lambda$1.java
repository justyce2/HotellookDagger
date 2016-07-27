package com.hotellook.ui.screen.hotel.prices;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class OffersFragment$$Lambda$1 implements Action1 {
    private final OffersFragment arg$1;

    private OffersFragment$$Lambda$1(OffersFragment offersFragment) {
        this.arg$1 = offersFragment;
    }

    public static Action1 lambdaFactory$(OffersFragment offersFragment) {
        return new OffersFragment$$Lambda$1(offersFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showState((OffersLoaderState) obj);
    }
}
