package com.hotellook.dependencies;

import android.app.Application;
import com.hotellook.api.abtesting.ABTesting;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.statistics.HLStatistics;
import com.hotellook.statistics.MixPanelEventsKeeper;
import com.hotellook.statistics.Statistics;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class StatisticsModule {
    @Singleton
    @Provides
    public MixPanelEventsKeeper provideMixPanelEventsKeeper() {
        return new MixPanelEventsKeeper();
    }

    @Singleton
    @Provides
    public Statistics provideStatistics(Application app, HotellookClient client, MixPanelEventsKeeper eventsKeeper) {
        return new HLStatistics(app, client, eventsKeeper);
    }

    @Singleton
    @Provides
    public ABTesting provideABTesting() {
        return StatisticsModule$$Lambda$1.lambdaFactory$();
    }
}
