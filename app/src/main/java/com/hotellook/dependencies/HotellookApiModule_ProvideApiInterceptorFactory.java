package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import okhttp3.Interceptor;

public final class HotellookApiModule_ProvideApiInterceptorFactory implements Factory<Interceptor> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookClient> clientProvider;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideApiInterceptorFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideApiInterceptorFactory(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || clientProvider != null) {
                this.clientProvider = clientProvider;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public Interceptor get() {
        return (Interceptor) Preconditions.checkNotNull(this.module.provideApiInterceptor((HotellookClient) this.clientProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Interceptor> create(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
        return new HotellookApiModule_ProvideApiInterceptorFactory(module, clientProvider);
    }
}
