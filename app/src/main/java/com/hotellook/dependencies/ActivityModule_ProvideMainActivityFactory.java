package com.hotellook.dependencies;

import com.hotellook.ui.activity.MainActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ActivityModule_ProvideMainActivityFactory implements Factory<MainActivity> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final ActivityModule module;

    static {
        $assertionsDisabled = !ActivityModule_ProvideMainActivityFactory.class.desiredAssertionStatus();
    }

    public ActivityModule_ProvideMainActivityFactory(ActivityModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public MainActivity get() {
        return (MainActivity) Preconditions.checkNotNull(this.module.provideMainActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MainActivity> create(ActivityModule module) {
        return new ActivityModule_ProvideMainActivityFactory(module);
    }
}
