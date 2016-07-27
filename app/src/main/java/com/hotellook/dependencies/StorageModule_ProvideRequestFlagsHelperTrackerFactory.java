package com.hotellook.dependencies;

import com.hotellook.api.trackers.RequestFlagsHelperTracker;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class StorageModule_ProvideRequestFlagsHelperTrackerFactory implements Factory<RequestFlagsHelperTracker> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final StorageModule module;

    static {
        $assertionsDisabled = !StorageModule_ProvideRequestFlagsHelperTrackerFactory.class.desiredAssertionStatus();
    }

    public StorageModule_ProvideRequestFlagsHelperTrackerFactory(StorageModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public RequestFlagsHelperTracker get() {
        return (RequestFlagsHelperTracker) Preconditions.checkNotNull(this.module.provideRequestFlagsHelperTracker(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<RequestFlagsHelperTracker> create(StorageModule module) {
        return new StorageModule_ProvideRequestFlagsHelperTrackerFactory(module);
    }
}
