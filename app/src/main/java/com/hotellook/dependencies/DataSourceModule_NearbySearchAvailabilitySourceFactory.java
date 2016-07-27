package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.NearbyCitiesAvailabilitySource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_NearbySearchAvailabilitySourceFactory implements Factory<NearbyCitiesAvailabilitySource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> apiProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_NearbySearchAvailabilitySourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_NearbySearchAvailabilitySourceFactory(DataSourceModule module, Provider<HotellookService> apiProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || apiProvider != null) {
                this.apiProvider = apiProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public NearbyCitiesAvailabilitySource get() {
        return (NearbyCitiesAvailabilitySource) Preconditions.checkNotNull(this.module.nearbySearchAvailabilitySource((HotellookService) this.apiProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<NearbyCitiesAvailabilitySource> create(DataSourceModule module, Provider<HotellookService> apiProvider) {
        return new DataSourceModule_NearbySearchAvailabilitySourceFactory(module, apiProvider);
    }
}
