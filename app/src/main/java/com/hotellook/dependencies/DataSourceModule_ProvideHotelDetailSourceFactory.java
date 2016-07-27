package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.HotelDetailSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideHotelDetailSourceFactory implements Factory<HotelDetailSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> hotellookServiceProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideHotelDetailSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideHotelDetailSourceFactory(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
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

    public HotelDetailSource get() {
        return (HotelDetailSource) Preconditions.checkNotNull(this.module.provideHotelDetailSource((HotellookService) this.hotellookServiceProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HotelDetailSource> create(DataSourceModule module, Provider<HotellookService> hotellookServiceProvider) {
        return new DataSourceModule_ProvideHotelDetailSourceFactory(module, hotellookServiceProvider);
    }
}
