package com.hotellook.dependencies;

import com.hotellook.api.trackers.RateDialogConditionsTracker;
import com.hotellook.utils.CommonPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideRateDialogConditionsTrackerFactory implements Factory<RateDialogConditionsTracker> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final StorageModule module;
    private final Provider<CommonPreferences> preferencesProvider;

    static {
        $assertionsDisabled = !StorageModule_ProvideRateDialogConditionsTrackerFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideRateDialogConditionsTrackerFactory(StorageModule module, Provider<CommonPreferences> preferencesProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || preferencesProvider != null) {
                this.preferencesProvider = preferencesProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public RateDialogConditionsTracker get() {
        return (RateDialogConditionsTracker) Preconditions.checkNotNull(this.module.provideRateDialogConditionsTracker((CommonPreferences) this.preferencesProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<RateDialogConditionsTracker> create(StorageModule module, Provider<CommonPreferences> preferencesProvider) {
        return new StorageModule_ProvideRateDialogConditionsTrackerFactory(module, preferencesProvider);
    }
}
