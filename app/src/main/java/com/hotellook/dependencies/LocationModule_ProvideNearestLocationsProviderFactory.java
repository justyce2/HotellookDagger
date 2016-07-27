package com.hotellook.dependencies;

import com.hotellook.location.LastKnownLocationProvider;
import com.hotellook.location.NearestLocationsProvider;
import com.hotellook.location.NearestLocationsSource;
import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LocationModule_ProvideNearestLocationsProviderFactory implements Factory<NearestLocationsProvider> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<EventBus> busProvider;
    private final Provider<LastKnownLocationProvider> locationProvider;
    private final LocationModule module;
    private final Provider<NearestLocationsSource> nearestLocationsSourceProvider;

    static {
        $assertionsDisabled = !LocationModule_ProvideNearestLocationsProviderFactory.class.desiredAssertionStatus();
    }

    public LocationModule_ProvideNearestLocationsProviderFactory(LocationModule module, Provider<LastKnownLocationProvider> locationProvider, Provider<NearestLocationsSource> nearestLocationsSourceProvider, Provider<EventBus> busProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || locationProvider != null) {
                this.locationProvider = locationProvider;
                if ($assertionsDisabled || nearestLocationsSourceProvider != null) {
                    this.nearestLocationsSourceProvider = nearestLocationsSourceProvider;
                    if ($assertionsDisabled || busProvider != null) {
                        this.busProvider = busProvider;
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

    public NearestLocationsProvider get() {
        return (NearestLocationsProvider) Preconditions.checkNotNull(this.module.provideNearestLocationsProvider((LastKnownLocationProvider) this.locationProvider.get(), (NearestLocationsSource) this.nearestLocationsSourceProvider.get(), (EventBus) this.busProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<NearestLocationsProvider> create(LocationModule module, Provider<LastKnownLocationProvider> locationProvider, Provider<NearestLocationsSource> nearestLocationsSourceProvider, Provider<EventBus> busProvider) {
        return new LocationModule_ProvideNearestLocationsProviderFactory(module, locationProvider, nearestLocationsSourceProvider, busProvider);
    }
}
