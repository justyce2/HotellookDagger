package com.hotellook.dependencies;

import com.hotellook.backstack.BackStackManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideBackStackManagerFactory implements Factory<BackStackManager> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final AppModule module;

    static {
        $assertionsDisabled = !AppModule_ProvideBackStackManagerFactory.class.desiredAssertionStatus();
    }

    public AppModule_ProvideBackStackManagerFactory(AppModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public BackStackManager get() {
        return (BackStackManager) Preconditions.checkNotNull(this.module.provideBackStackManager(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<BackStackManager> create(AppModule module) {
        return new AppModule_ProvideBackStackManagerFactory(module);
    }
}
