package com.hotellook.statistics;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HLStatistics$$Lambda$1 implements Runnable {
    private final HLStatistics arg$1;
    private final String arg$2;

    private HLStatistics$$Lambda$1(HLStatistics hLStatistics, String str) {
        this.arg$1 = hLStatistics;
        this.arg$2 = str;
    }

    public static Runnable lambdaFactory$(HLStatistics hLStatistics, String str) {
        return new HLStatistics$$Lambda$1(hLStatistics, str);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$trackMixPanel$0(this.arg$2);
    }
}
