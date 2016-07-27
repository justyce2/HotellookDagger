package com.hotellook.api.exchange;

import android.support.annotation.NonNull;
import com.hotellook.core.api.HotellookService;
import rx.Observable;
import timber.log.Timber;

public class ExchangeRateSource {
    private static final String TARGET_CURRENCY = "usd";
    private final HotellookService api;

    public ExchangeRateSource(HotellookService api) {
        this.api = api;
    }

    public Observable<Double> observeExchange(@NonNull String baseCurrency) {
        String lowerCaseCurrency = baseCurrency.toLowerCase();
        return this.api.observeExchangeRate(lowerCaseCurrency, TARGET_CURRENCY).doOnNext(ExchangeRateSource$$Lambda$1.lambdaFactory$(this, lowerCaseCurrency)).doOnError(ExchangeRateSource$$Lambda$2.lambdaFactory$(this, lowerCaseCurrency));
    }

    /* synthetic */ void lambda$observeExchange$0(String lowerCaseCurrency, Double exchangeRate) {
        logSuccess(lowerCaseCurrency, exchangeRate);
    }

    /* synthetic */ void lambda$observeExchange$1(String lowerCaseCurrency, Throwable error) {
        logError(error, lowerCaseCurrency);
    }

    private void logError(Throwable error, String baseCurrency) {
        Timber.m751d("Unable to load exchange rate for %s. Error: %s", baseCurrency, error.getMessage());
    }

    private void logSuccess(String baseCurrency, Double exchangeRate) {
        Timber.m751d("Exchange from %s to %s: %f", baseCurrency, TARGET_CURRENCY, exchangeRate);
    }
}
