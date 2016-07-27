package com.hotellook.dependencies;

import android.graphics.Color;
import com.hotellook.api.abtesting.ABTesting;
import com.hotellook.api.abtesting.HLABTesting;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class StatisticsModule$$Lambda$1 implements ABTesting {
    private static final StatisticsModule$$Lambda$1 instance;

    static {
        instance = new StatisticsModule$$Lambda$1();
    }

    private StatisticsModule$$Lambda$1() {
    }

    public static ABTesting lambdaFactory$() {
        return instance;
    }

    @Hidden
    public int getCTAButtonColor() {
        return Color.parseColor(HLABTesting.DEFAULT_CPA_BTN_COLOR);
    }
}
