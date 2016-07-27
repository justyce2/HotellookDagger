package com.hotellook.dependencies;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideAppFactory implements Factory<Application> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final AppModule module;

    static {
        $assertionsDisabled = !AppModule_ProvideAppFactory.class.desiredAssertionStatus();
    }

    public AppModule_ProvideAppFactory(AppModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public Application get() {
        return (Application) Preconditions.checkNotNull(this.module.provideApp(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Application> create(AppModule module) {
        return new AppModule_ProvideAppFactory(module);
    }
}
