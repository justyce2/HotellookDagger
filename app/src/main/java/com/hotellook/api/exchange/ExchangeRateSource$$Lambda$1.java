package com.hotellook.api.exchange;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class ExchangeRateSource$$Lambda$1 implements Action1 {
    private final ExchangeRateSource arg$1;
    private final String arg$2;

    private ExchangeRateSource$$Lambda$1(ExchangeRateSource exchangeRateSource, String str) {
        this.arg$1 = exchangeRateSource;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(ExchangeRateSource exchangeRateSource, String str) {
        return new ExchangeRateSource$$Lambda$1(exchangeRateSource, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$observeExchange$0(this.arg$2, (Double) obj);
    }
}
