package com.hotellook.api.dataloaders;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class MinPricesLoader$$Lambda$2 implements Action1 {
    private final MinPricesLoader arg$1;

    private MinPricesLoader$$Lambda$2(MinPricesLoader minPricesLoader) {
        this.arg$1 = minPricesLoader;
    }

    public static Action1 lambdaFactory$(MinPricesLoader minPricesLoader) {
        return new MinPricesLoader$$Lambda$2(minPricesLoader);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$load$1((Throwable) obj);
    }
}
