package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.core.api.HotellookService;
import com.hotellook.location.LastKnownLocationProvider;
import com.hotellook.location.LocationRequestResolver;
import com.hotellook.location.NearestLocationsProvider;
import com.hotellook.location.NearestLocationsSource;
import com.hotellook.utils.EventBus;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class LocationModule {
    @Singleton
    @Provides
    public LastKnownLocationProvider provideLastKnownLocationProvider(Application app, LocationRequestResolver locationRequestResolver, ReactiveLocationProvider locationProvider) {
        return new LastKnownLocationProvider(app, locationRequestResolver, locationProvider);
    }

    @Singleton
    @Provides
    public NearestLocationsProvider provideNearestLocationsProvider(LastKnownLocationProvider locationProvider, NearestLocationsSource nearestLocationsSource, EventBus bus) {
        NearestLocationsProvider provider = new NearestLocationsProvider(locationProvider, nearestLocationsSource);
        bus.register(provider);
        return provider;
    }

    @Provides
    public NearestLocationsSource provideNearestLocationsSource(HotellookService api) {
        return new NearestLocationsSource(api);
    }

    @Singleton
    @Provides
    public LocationRequestResolver provideLocationRequestResolver(Application context, ReactiveLocationProvider locationProvider) {
        return new LocationRequestResolver(context, locationProvider);
    }

    @Singleton
    @Provides
    public ReactiveLocationProvider provideLocationProvider(Application context) {
        return new ReactiveLocationProvider(context);
    }
}
