package com.hotellook.dependencies;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public final class HotellookApiModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Interceptor> apiInterceptorProvider;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideOkHttpClientFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideOkHttpClientFactory(HotellookApiModule module, Provider<Interceptor> apiInterceptorProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || apiInterceptorProvider != null) {
                this.apiInterceptorProvider = apiInterceptorProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public OkHttpClient get() {
        return (OkHttpClient) Preconditions.checkNotNull(this.module.provideOkHttpClient((Interceptor) this.apiInterceptorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<OkHttpClient> create(HotellookApiModule module, Provider<Interceptor> apiInterceptorProvider) {
        return new HotellookApiModule_ProvideOkHttpClientFactory(module, apiInterceptorProvider);
    }
}
