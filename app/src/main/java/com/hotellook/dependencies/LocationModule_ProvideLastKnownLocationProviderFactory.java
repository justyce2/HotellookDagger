package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.location.LastKnownLocationProvider;
import com.hotellook.location.LocationRequestResolver;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

public final class LocationModule_ProvideLastKnownLocationProviderFactory implements Factory<LastKnownLocationProvider> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<ReactiveLocationProvider> locationProvider;
    private final Provider<LocationRequestResolver> locationRequestResolverProvider;
    private final LocationModule module;

    static {
        $assertionsDisabled = !LocationModule_ProvideLastKnownLocationProviderFactory.class.desiredAssertionStatus();
    }

    public LocationModule_ProvideLastKnownLocationProviderFactory(LocationModule module, Provider<Application> appProvider, Provider<LocationRequestResolver> locationRequestResolverProvider, Provider<ReactiveLocationProvider> locationProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || locationRequestResolverProvider != null) {
                    this.locationRequestResolverProvider = locationRequestResolverProvider;
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
        throw new AssertionError();
    }

    public LastKnownLocationProvider get() {
        return (LastKnownLocationProvider) Preconditions.checkNotNull(this.module.provideLastKnownLocationProvider((Application) this.appProvider.get(), (LocationRequestResolver) this.locationRequestResolverProvider.get(), (ReactiveLocationProvider) this.locationProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LastKnownLocationProvider> create(LocationModule module, Provider<Application> appProvider, Provider<LocationRequestResolver> locationRequestResolverProvider, Provider<ReactiveLocationProvider> locationProvider) {
        return new LocationModule_ProvideLastKnownLocationProviderFactory(module, appProvider, locationRequestResolverProvider, locationProvider);
    }
}
