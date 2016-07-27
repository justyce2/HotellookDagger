package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.RoomTypesSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideRoomTypesSourceFactory implements Factory<RoomTypesSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideRoomTypesSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideRoomTypesSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
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

    public RoomTypesSource get() {
        return (RoomTypesSource) Preconditions.checkNotNull(this.module.provideRoomTypesSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<RoomTypesSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideRoomTypesSourceFactory(module, hotellookServiceProvider);
    }
}
