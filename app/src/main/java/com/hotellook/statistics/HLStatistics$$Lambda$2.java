package com.hotellook.statistics;

import com.hotellook.statistics.mixpanel.MixPanelParamsBuilder;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HLStatistics$$Lambda$2 implements Runnable {
    private final HLStatistics arg$1;
    private final String arg$2;
    private final MixPanelParamsBuilder arg$3;

    private HLStatistics$$Lambda$2(HLStatistics hLStatistics, String str, MixPanelParamsBuilder mixPanelParamsBuilder) {
        this.arg$1 = hLStatistics;
        this.arg$2 = str;
        this.arg$3 = mixPanelParamsBuilder;
    }

    public static Runnable lambdaFactory$(HLStatistics hLStatistics, String str, MixPanelParamsBuilder mixPanelParamsBuilder) {
        return new HLStatistics$$Lambda$2(hLStatistics, str, mixPanelParamsBuilder);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$trackMixPanel$1(this.arg$2, this.arg$3);
    }
}
