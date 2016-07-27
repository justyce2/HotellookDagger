package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.LocationDumpSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideHotelsSourceFactory implements Factory<LocationDumpSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideHotelsSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideHotelsSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
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

    public LocationDumpSource get() {
        return (LocationDumpSource) Preconditions.checkNotNull(this.module.provideHotelsSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LocationDumpSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideHotelsSourceFactory(module, hotellookServiceProvider);
    }
}
