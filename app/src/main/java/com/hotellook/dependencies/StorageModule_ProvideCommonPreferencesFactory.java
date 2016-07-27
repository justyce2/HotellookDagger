package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideCommonPreferencesFactory implements Factory<CommonPreferences> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideCommonPreferencesFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideCommonPreferencesFactory(StorageModule module, Provider<Application> appProvider) {
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

    public CommonPreferences get() {
        return (CommonPreferences) Preconditions.checkNotNull(this.module.provideCommonPreferences((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<CommonPreferences> create(StorageModule module, Provider<Application> appProvider) {
        return new StorageModule_ProvideCommonPreferencesFactory(module, appProvider);
    }
}
