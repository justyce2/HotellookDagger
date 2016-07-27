package com.hotellook.dependencies;

import com.hotellook.api.exchange.CurrencyConverter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class StorageModule_CurrencyConverterFactory implements Factory<CurrencyConverter> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_CurrencyConverterFactory.class.desiredAssertionStatus();
    }

    public StorageModule_CurrencyConverterFactory(StorageModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public CurrencyConverter get() {
        return (CurrencyConverter) Preconditions.checkNotNull(this.module.currencyConverter(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<CurrencyConverter> create(StorageModule module) {
        return new StorageModule_CurrencyConverterFactory(module);
    }
}
