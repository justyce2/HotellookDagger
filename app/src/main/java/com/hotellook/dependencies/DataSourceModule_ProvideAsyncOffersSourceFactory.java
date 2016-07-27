package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookService;
import com.hotellook.search.AsyncOffersSource;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataSourceModule_ProvideAsyncOffersSourceFactory implements Factory<AsyncOffersSource> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookService> apiProvider;
    private final DataSourceModule module;
    private final Provider<String> tokenProvider;

    static {
        $assertionsDisabled = !DataSourceModule_ProvideAsyncOffersSourceFactory.class.desiredAssertionStatus();
    }

    public DataSourceModule_ProvideAsyncOffersSourceFactory(DataSourceModule module, Provider<HotellookService> apiProvider, Provider<String> tokenProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || apiProvider != null) {
                this.apiProvider = apiProvider;
                if ($assertionsDisabled || tokenProvider != null) {
                    this.tokenProvider = tokenProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AsyncOffersSource get() {
        return (AsyncOffersSource) Preconditions.checkNotNull(this.module.provideAsyncOffersSource((HotellookService) this.apiProvider.get(), (String) this.tokenProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AsyncOffersSource> create(DataSourceModule module, Provider<HotellookService> apiProvider, Provider<String> tokenProvider) {
        return new DataSourceModule_ProvideAsyncOffersSourceFactory(module, apiProvider, tokenProvider);
    }
}
