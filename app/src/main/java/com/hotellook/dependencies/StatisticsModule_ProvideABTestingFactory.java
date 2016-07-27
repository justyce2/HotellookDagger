package com.hotellook.dependencies;

import com.hotellook.api.abtesting.ABTesting;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class StatisticsModule_ProvideABTestingFactory implements Factory<ABTesting> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private final StatisticsModule module;

    static {
        $assertionsDisabled = !StatisticsModule_ProvideABTestingFactory.class.desiredAssertionStatus();
    }

    public StatisticsModule_ProvideABTestingFactory(StatisticsModule module) {
        if ($assertionsDisabled || module != null) {
            this.module = module;
            return;
        }
        throw new AssertionError();
    }

    public ABTesting get() {
        return (ABTesting) Preconditions.checkNotNull(this.module.provideABTesting(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ABTesting> create(StatisticsModule module) {
        return new StatisticsModule_ProvideABTestingFactory(module);
    }
}
