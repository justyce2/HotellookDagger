package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.data.SearchFormPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideSearchFormPreferencesFactory implements Factory<SearchFormPreferences> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideSearchFormPreferencesFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideSearchFormPreferencesFactory(StorageModule module, Provider<Application> appProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SearchFormPreferences get() {
        return (SearchFormPreferences) Preconditions.checkNotNull(this.module.provideSearchFormPreferences((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchFormPreferences> create(StorageModule module, Provider<Application> appProvider) {
        return new StorageModule_ProvideSearchFormPreferencesFactory(module, appProvider);
    }
}
