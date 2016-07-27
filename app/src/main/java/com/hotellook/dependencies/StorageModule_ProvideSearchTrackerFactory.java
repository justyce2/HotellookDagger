package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.trackers.SearchTracker;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StorageModule_ProvideSearchTrackerFactory implements Factory<SearchTracker> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideSearchTrackerFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideSearchTrackerFactory(StorageModule module, Provider<Application> appProvider) {
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

    public SearchTracker get() {
        return (SearchTracker) Preconditions.checkNotNull(this.module.provideSearchTracker((Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SearchTracker> create(StorageModule module, Provider<Application> appProvider) {
        return new StorageModule_ProvideSearchTrackerFactory(module, appProvider);
    }
}
