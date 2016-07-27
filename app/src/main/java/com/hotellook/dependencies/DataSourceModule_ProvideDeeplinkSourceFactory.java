package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.core.api.HotellookService;
import com.hotellook.ui.screen.hotel.api.BookingSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideDeeplinkSourceFactory implements Factory<BookingSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> apiProvider;
    private final Provider<Application> appProvider;
    private final DataSourceModule module;
    private final Provider<String> tokenProvider;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideDeeplinkSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideDeeplinkSourceFactory(DataSourceModule module, Provider<HotellookService> apiProvider, Provider<String> tokenProvider, Provider<Application> appProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || apiProvider != null) {
                this.apiProvider = apiProvider;
                if ($assertionsDisabled || tokenProvider != null) {
                    this.tokenProvider = tokenProvider;
                    if ($assertionsDisabled || appProvider != null) {
                        this.appProvider = appProvider;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public BookingSource get() {
        return (BookingSource) Preconditions.checkNotNull(this.module.provideDeeplinkSource((HotellookService) this.apiProvider.get(), (String) this.tokenProvider.get(), (Application) this.appProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<BookingSource> create(DataSourceModule module, Provider<HotellookService> apiProvider, Provider<String> tokenProvider, Provider<Application> appProvider) {
        return new DataSourceModule_ProvideDeeplinkSourceFactory(module, apiProvider, tokenProvider, appProvider);
    }
}
