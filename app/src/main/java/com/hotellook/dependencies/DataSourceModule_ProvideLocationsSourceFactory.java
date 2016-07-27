package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.LocationsSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideLocationsSourceFactory implements Factory<LocationsSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideLocationsSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideLocationsSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || hotellookServiceProvider != null) {
                this.hotellookServiceProvider = hotellookServiceProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public LocationsSource get() {
        return (LocationsSource) Preconditions.checkNotNull(this.module.provideLocationsSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LocationsSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideLocationsSourceFactory(module, hotellookServiceProvider);
    }
}
