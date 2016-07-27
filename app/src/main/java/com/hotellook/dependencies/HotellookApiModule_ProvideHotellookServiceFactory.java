package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookClient;
import com.hotellook.core.api.HotellookService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HotellookApiModule_ProvideHotellookServiceFactory implements Factory<HotellookService> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookClient> clientProvider;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideHotellookServiceFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideHotellookServiceFactory(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
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

    public HotellookService get() {
        return (HotellookService) Preconditions.checkNotNull(this.module.provideHotellookService((HotellookClient) this.clientProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HotellookService> create(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
        return new HotellookApiModule_ProvideHotellookServiceFactory(module, clientProvider);
    }
}
