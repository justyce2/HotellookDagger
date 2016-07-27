package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.ui.screen.hotel.data.HotelDataSource;
import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideHotelDataSourceFactory implements Factory<HotelDataSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<EventBus> eventBusProvider;
    private final DataSourceModule module;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideHotelDataSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideHotelDataSourceFactory(DataSourceModule module, Provider<Application> appProvider, Provider<EventBus> eventBusProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || eventBusProvider != null) {
                    this.eventBusProvider = eventBusProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public HotelDataSource get() {
        return (HotelDataSource) Preconditions.checkNotNull(this.module.provideHotelDataSource((Application) this.appProvider.get(), (EventBus) this.eventBusProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HotelDataSource> create(DataSourceModule module, Provider<Application> appProvider, Provider<EventBus> eventBusProvider) {
        return new DataSourceModule_ProvideHotelDataSourceFactory(module, appProvider, eventBusProvider);
    }
}
