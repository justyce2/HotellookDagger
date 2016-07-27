package com.hotellook.dependencies;

import com.hotellook.search.DumpSource;
import com.hotellook.search.LocationDumpSource;
import com.hotellook.search.LocationsSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideDumpFactory implements Factory<DumpSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<LocationDumpSource> locationDumpSourceProvider;
    private final Provider<LocationsSource> locationsSourceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideDumpFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideDumpFactory(DataSourceModule module, Provider<LocationsSource> locationsSourceProvider, Provider<LocationDumpSource> locationDumpSourceProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || locationsSourceProvider != null) {
                this.locationsSourceProvider = locationsSourceProvider;
                if ($assertionsDisabled || locationDumpSourceProvider != null) {
                    this.locationDumpSourceProvider = locationDumpSourceProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DumpSource get() {
        return (DumpSource) Preconditions.checkNotNull(this.module.provideDump((LocationsSource) this.locationsSourceProvider.get(), (LocationDumpSource) this.locationDumpSourceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DumpSource> create(DataSourceModule module, Provider<LocationsSource> locationsSourceProvider, Provider<LocationDumpSource> locationDumpSourceProvider) {
        return new DataSourceModule_ProvideDumpFactory(module, locationsSourceProvider, locationDumpSourceProvider);
    }
}
