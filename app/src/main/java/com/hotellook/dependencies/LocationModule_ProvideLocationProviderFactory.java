package com.hotellook.dependencies;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public final class LocationModule_ProvideLocationProviderFactory implements Factory<ReactiveLocationProvider> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> contextProvider;
    private final LocationModule module;

    static {
        $assertionsDisabled = !LocationModule_ProvideLocationProviderFactory.class.desiredAssertionStatus();
    }

    public LocationModule_ProvideLocationProviderFactory(LocationModule module, Provider<Application> contextProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || contextProvider != null) {
                this.contextProvider = contextProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ReactiveLocationProvider get() {
        return (ReactiveLocationProvider) Preconditions.checkNotNull(this.module.provideLocationProvider((Application) this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ReactiveLocationProvider> create(LocationModule module, Provider<Application> contextProvider) {
        return new LocationModule_ProvideLocationProviderFactory(module, contextProvider);
    }
}
