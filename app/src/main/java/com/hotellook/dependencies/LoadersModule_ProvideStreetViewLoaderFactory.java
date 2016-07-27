package com.hotellook.dependencies;

import com.hotellook.api.dataloaders.StreetViewCheckLoader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

public final class LoadersModule_ProvideStreetViewLoaderFactory implements Factory<StreetViewCheckLoader> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final LoadersModule module;
    private final Provider<OkHttpClient> okHttpClientProvider;

    static {
        $assertionsDisabled = !LoadersModule_ProvideStreetViewLoaderFactory.class.desiredAssertionStatus();
    }

    public LoadersModule_ProvideStreetViewLoaderFactory(LoadersModule module, Provider<OkHttpClient> okHttpClientProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || okHttpClientProvider != null) {
                this.okHttpClientProvider = okHttpClientProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public StreetViewCheckLoader get() {
        return (StreetViewCheckLoader) Preconditions.checkNotNull(this.module.provideStreetViewLoader((OkHttpClient) this.okHttpClientProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<StreetViewCheckLoader> create(LoadersModule module, Provider<OkHttpClient> okHttpClientProvider) {
        return new LoadersModule_ProvideStreetViewLoaderFactory(module, okHttpClientProvider);
    }
}
