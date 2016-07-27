package com.hotellook.dependencies;

import com.hotellook.currency.CurrencyRepository;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideCurrencyRepositoryFactory implements Factory<CurrencyRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<CommonPreferences> commonPreferencesProvider;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideCurrencyRepositoryFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideCurrencyRepositoryFactory(StorageModule module, Provider<CommonPreferences> commonPreferencesProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || commonPreferencesProvider != null) {
                this.commonPreferencesProvider = commonPreferencesProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public CurrencyRepository get() {
        return (CurrencyRepository) Preconditions.checkNotNull(this.module.provideCurrencyRepository((CommonPreferences) this.commonPreferencesProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<CurrencyRepository> create(StorageModule module, Provider<CommonPreferences> commonPreferencesProvider) {
        return new StorageModule_ProvideCurrencyRepositoryFactory(module, commonPreferencesProvider);
    }
}
