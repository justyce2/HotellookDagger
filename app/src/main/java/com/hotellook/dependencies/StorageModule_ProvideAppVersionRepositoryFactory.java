package com.hotellook.dependencies;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideAppVersionRepositoryFactory implements Factory<AppVersionRepository> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideAppVersionRepositoryFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideAppVersionRepositoryFactory(StorageModule module, Provider<Application> appProvider) {
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

    public AppVersionRepository get() {
        return (AppVersionRepository) Preconditions.checkNotNull(this.module.provideAppVersionRepository((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AppVersionRepository> create(StorageModule module, Provider<Application> appProvider) {
        return new StorageModule_ProvideAppVersionRepositoryFactory(module, appProvider);
    }
}
