package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.statistics.Statistics;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class StatisticsModule_ProvideStatisticsFactory implements Factory<Statistics> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final Provider<Application> appProvider;
    private final Provider<HotellookClient> clientProvider;
    private final Provider<MixPanelEventsKeeper> eventsKeeperProvider;
    private final StatisticsModule module;

    static {
        $assertionsDisabled = !StatisticsModule_ProvideStatisticsFactory.class.desiredAssertionStatus();
    }

    public StatisticsModule_ProvideStatisticsFactory(StatisticsModule module, Provider<Application> appProvider, Provider<HotellookClient> clientProvider, Provider<MixPanelEventsKeeper> eventsKeeperProvider) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            if ($assertionsDisabled || appProvider != null) {
                this.appProvider = appProvider;
                if ($assertionsDisabled || clientProvider != null) {
                    this.clientProvider = clientProvider;
                    if ($assertionsDisabled || eventsKeeperProvider != null) {
                        this.eventsKeeperProvider = eventsKeeperProvider;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public Statistics get() {
        return (Statistics) Preconditions.checkNotNull(this.module.provideStatistics((Application) this.appProvider.get(), (HotellookClient) this.clientProvider.get(), (MixPanelEventsKeeper) this.eventsKeeperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Statistics> create(StatisticsModule module, Provider<Application> appProvider, Provider<HotellookClient> clientProvider, Provider<MixPanelEventsKeeper> eventsKeeperProvider) {
        return new StatisticsModule_ProvideStatisticsFactory(module, appProvider, clientProvider, eventsKeeperProvider);
    }
}
