package com.hotellook.dependencies;

import com.hotellook.backstack.SnapshotKeeper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideSnapshotKeeperFactory implements Factory<SnapshotKeeper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final AppModule module;

    static {
        $assertionsDisabled = !AppModule_ProvideSnapshotKeeperFactory.class.desiredAssertionStatus();
    }

    public AppModule_ProvideSnapshotKeeperFactory(AppModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public SnapshotKeeper get() {
        return (SnapshotKeeper) Preconditions.checkNotNull(this.module.provideSnapshotKeeper(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SnapshotKeeper> create(AppModule module) {
        return new AppModule_ProvideSnapshotKeeperFactory(module);
    }
}
