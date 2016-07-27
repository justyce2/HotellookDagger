package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.data.SearchFormData;
import com.hotellook.api.data.SearchFormPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideSearchFormDataFactory implements Factory<SearchFormData> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final StorageModule module;
    private final Provider<SearchFormPreferences> preferencesProvider;

    static {
        $assertionsDisabled = !StorageModule_ProvideSearchFormDataFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideSearchFormDataFactory(StorageModule module, Provider<Application> appProvider, Provider<SearchFormPreferences> preferencesProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || preferencesProvider != null) {
                    this.preferencesProvider = preferencesProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SearchFormData get() {
        return (SearchFormData) Preconditions.checkNotNull(this.module.provideSearchFormData((Application) this.appProvider.get(), (SearchFormPreferences) this.preferencesProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchFormData> create(StorageModule module, Provider<Application> appProvider, Provider<SearchFormPreferences> preferencesProvider) {
        return new StorageModule_ProvideSearchFormDataFactory(module, appProvider, preferencesProvider);
    }
}
