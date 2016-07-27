package com.hotellook.dependencies;

import com.hotellook.api.exchange.ExchangeRateSource;
import com.hotellook.core.api.HotellookService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideExchangeRateSourceFactory implements Factory<ExchangeRateSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideExchangeRateSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideExchangeRateSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || hotellookServiceProvider != null) {
                this.hotellookServiceProvider = hotellookServiceProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ExchangeRateSource get() {
        return (ExchangeRateSource) Preconditions.checkNotNull(this.module.provideExchangeRateSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ExchangeRateSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideExchangeRateSourceFactory(module, hotellookServiceProvider);
    }
}
