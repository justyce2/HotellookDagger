package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.location.NearestLocationsSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class LocationModule_ProvideNearestLocationsSourceFactory implements Factory<NearestLocationsSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> apiProvider;
    private final LocationModule module;

    static {
        $assertionsDisabled = !LocationModule_ProvideNearestLocationsSourceFactory.class.desiredAssertionStatus();
    }

    public LocationModule_ProvideNearestLocationsSourceFactory(LocationModule module, Provider<HotellookService> apiProvider) {
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

    public NearestLocationsSource get() {
        return (NearestLocationsSource) Preconditions.checkNotNull(this.module.provideNearestLocationsSource((HotellookService) this.apiProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<NearestLocationsSource> create(LocationModule module, Provider<HotellookService> apiProvider) {
        return new LocationModule_ProvideNearestLocationsSourceFactory(module, apiProvider);
    }
}
