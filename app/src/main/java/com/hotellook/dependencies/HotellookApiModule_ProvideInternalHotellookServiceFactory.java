package com.hotellook.dependencies;

import com.hotellook.api.ShortUrlService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class HotellookApiModule_ProvideInternalHotellookServiceFactory implements Factory<ShortUrlService> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideInternalHotellookServiceFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideInternalHotellookServiceFactory(HotellookApiModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public ShortUrlService get() {
        return (ShortUrlService) Preconditions.checkNotNull(this.module.provideInternalHotellookService(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ShortUrlService> create(HotellookApiModule module) {
        return new HotellookApiModule_ProvideInternalHotellookServiceFactory(module);
    }
}
