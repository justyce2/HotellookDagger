package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookClient;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HotellookApiModule_ProvideHostFactory implements Factory<String> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<HotellookClient> clientProvider;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideHostFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideHostFactory(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
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

    public String get() {
        return (String) Preconditions.checkNotNull(this.module.provideHost((HotellookClient) this.clientProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<String> create(HotellookApiModule module, Provider<HotellookClient> clientProvider) {
        return new HotellookApiModule_ProvideHostFactory(module, clientProvider);
    }
}
