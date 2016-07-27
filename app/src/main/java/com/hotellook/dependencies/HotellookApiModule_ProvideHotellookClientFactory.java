package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.utils.EventBus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HotellookApiModule_ProvideHotellookClientFactory implements Factory<HotellookClient> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<EventBus> busProvider;
    private final HotellookApiModule module;

    static {
        $assertionsDisabled = !HotellookApiModule_ProvideHotellookClientFactory.class.desiredAssertionStatus();
    }

    public HotellookApiModule_ProvideHotellookClientFactory(HotellookApiModule module, Provider<Application> appProvider, Provider<EventBus> busProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || busProvider != null) {
                    this.busProvider = busProvider;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public HotellookClient get() {
        return (HotellookClient) Preconditions.checkNotNull(this.module.provideHotellookClient((Application) this.appProvider.get(), (EventBus) this.busProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HotellookClient> create(HotellookApiModule module, Provider<Application> appProvider, Provider<EventBus> busProvider) {
        return new HotellookApiModule_ProvideHotellookClientFactory(module, appProvider, busProvider);
    }
}
