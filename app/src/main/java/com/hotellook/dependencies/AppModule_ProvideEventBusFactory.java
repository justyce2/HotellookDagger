package com.hotellook.dependencies;

import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideEventBusFactory implements Factory<EventBus> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final AppModule module;

    static {
        $assertionsDisabled = !AppModule_ProvideEventBusFactory.class.desiredAssertionStatus();
    }

    public AppModule_ProvideEventBusFactory(AppModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public EventBus get() {
        return (EventBus) Preconditions.checkNotNull(this.module.provideEventBus(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<EventBus> create(AppModule module) {
        return new AppModule_ProvideEventBusFactory(module);
    }
}
