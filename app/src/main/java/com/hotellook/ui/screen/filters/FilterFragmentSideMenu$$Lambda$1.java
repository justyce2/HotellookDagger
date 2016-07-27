package com.hotellook.ui.screen.filters;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FilterFragmentSideMenu$$Lambda$1 implements Runnable {
    private final FilterFragmentSideMenu arg$1;

    private FilterFragmentSideMenu$$Lambda$1(FilterFragmentSideMenu filterFragmentSideMenu) {
        this.arg$1 = filterFragmentSideMenu;
    }

    public static Runnable lambdaFactory$(FilterFragmentSideMenu filterFragmentSideMenu) {
        return new FilterFragmentSideMenu$$Lambda$1(filterFragmentSideMenu);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$onViewCreated$0();
    }
}
