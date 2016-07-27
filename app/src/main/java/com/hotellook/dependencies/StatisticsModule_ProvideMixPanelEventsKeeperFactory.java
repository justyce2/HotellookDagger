package com.hotellook.dependencies;

import com.hotellook.statistics.MixPanelEventsKeeper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class StatisticsModule_ProvideMixPanelEventsKeeperFactory implements Factory<MixPanelEventsKeeper> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final StatisticsModule module;

    static {
        $assertionsDisabled = !StatisticsModule_ProvideMixPanelEventsKeeperFactory.class.desiredAssertionStatus();
    }

    public StatisticsModule_ProvideMixPanelEventsKeeperFactory(StatisticsModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public MixPanelEventsKeeper get() {
        return (MixPanelEventsKeeper) Preconditions.checkNotNull(this.module.provideMixPanelEventsKeeper(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MixPanelEventsKeeper> create(StatisticsModule module) {
        return new StatisticsModule_ProvideMixPanelEventsKeeperFactory(module);
    }
}
