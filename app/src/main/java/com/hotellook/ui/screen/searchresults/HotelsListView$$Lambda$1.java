package com.hotellook.ui.screen.searchresults;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelsListView$$Lambda$1 implements Runnable {
    private final HotelsListView arg$1;

    private HotelsListView$$Lambda$1(HotelsListView hotelsListView) {
        this.arg$1 = hotelsListView;
    }

    public static Runnable lambdaFactory$(HotelsListView hotelsListView) {
        return new HotelsListView$$Lambda$1(hotelsListView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$updateList$0();
    }
}
