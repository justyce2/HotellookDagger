package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookImageUrlProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class HotellookApiModule_ProvideHotellookImageUrlProviderFactory implements Factory<HotellookImageUrlProvider> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideHotellookImageUrlProviderFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideHotellookImageUrlProviderFactory(HotellookApiModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public HotellookImageUrlProvider get() {
        return (HotellookImageUrlProvider) Preconditions.checkNotNull(this.module.provideHotellookImageUrlProvider(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HotellookImageUrlProvider> create(HotellookApiModule module) {
        return new HotellookApiModule_ProvideHotellookImageUrlProviderFactory(module);
    }
}
