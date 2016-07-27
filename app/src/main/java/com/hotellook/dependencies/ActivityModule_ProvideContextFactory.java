package com.hotellook.dependencies;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ActivityModule_ProvideContextFactory implements Factory<Context> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final ActivityModule module;

    static {
        $assertionsDisabled = !ActivityModule_ProvideContextFactory.class.desiredAssertionStatus();
    }

    public ActivityModule_ProvideContextFactory(ActivityModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public Context get() {
        return (Context) Preconditions.checkNotNull(this.module.provideContext(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Context> create(ActivityModule module) {
        return new ActivityModule_ProvideContextFactory(module);
    }
}
