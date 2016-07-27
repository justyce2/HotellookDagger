package com.hotellook.dependencies;

import com.hotellook.api.dataloaders.MinPricesLoader;
import com.hotellook.core.api.HotellookService;
import com.hotellook.currency.CurrencyRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LoadersModule_ProvideMinPricesLoaderFactory implements Factory<MinPricesLoader> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CurrencyRepository> currencyRepositoryProvider;
    private final LoadersModule module;
    private final Provider<HotellookService> serviceProvider;

    static {
        $assertionsDisabled = !LoadersModule_ProvideMinPricesLoaderFactory.class.desiredAssertionStatus();
    }

    public LoadersModule_ProvideMinPricesLoaderFactory(LoadersModule module, Provider<HotellookService> serviceProvider, Provider<CurrencyRepository> currencyRepositoryProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || serviceProvider != null) {
                this.serviceProvider = serviceProvider;
                if ($assertionsDisabled || currencyRepositoryProvider != null) {
                    this.currencyRepositoryProvider = currencyRepositoryProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public MinPricesLoader get() {
        return (MinPricesLoader) Preconditions.checkNotNull(this.module.provideMinPricesLoader((HotellookService) this.serviceProvider.get(), (CurrencyRepository) this.currencyRepositoryProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MinPricesLoader> create(LoadersModule module, Provider<HotellookService> serviceProvider, Provider<CurrencyRepository> currencyRepositoryProvider) {
        return new LoadersModule_ProvideMinPricesLoaderFactory(module, serviceProvider, currencyRepositoryProvider);
    }
}
