package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.location.LocationRequestResolver;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public final class LocationModule_ProvideLocationRequestResolverFactory implements Factory<LocationRequestResolver> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> contextProvider;
    private final Provider<ReactiveLocationProvider> locationProvider;
    private final LocationModule module;

    static {
        $assertionsDisabled = !LocationModule_ProvideLocationRequestResolverFactory.class.desiredAssertionStatus();
    }

    public LocationModule_ProvideLocationRequestResolverFactory(LocationModule module, Provider<Application> contextProvider, Provider<ReactiveLocationProvider> locationProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || contextProvider != null) {
                this.contextProvider = contextProvider;
                if ($assertionsDisabled || locationProvider != null) {
                    this.locationProvider = locationProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public LocationRequestResolver get() {
        return (LocationRequestResolver) Preconditions.checkNotNull(this.module.provideLocationRequestResolver((Application) this.contextProvider.get(), (ReactiveLocationProvider) this.locationProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LocationRequestResolver> create(LocationModule module, Provider<Application> contextProvider, Provider<ReactiveLocationProvider> locationProvider) {
        return new LocationModule_ProvideLocationRequestResolverFactory(module, contextProvider, locationProvider);
    }
}
