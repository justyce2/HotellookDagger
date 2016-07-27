package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.LocationIdsSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideLocationIdsSourceFactory implements Factory<LocationIdsSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideLocationIdsSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideLocationIdsSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
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

    public LocationIdsSource get() {
        return (LocationIdsSource) Preconditions.checkNotNull(this.module.provideLocationIdsSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LocationIdsSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideLocationIdsSourceFactory(module, hotellookServiceProvider);
    }
}
