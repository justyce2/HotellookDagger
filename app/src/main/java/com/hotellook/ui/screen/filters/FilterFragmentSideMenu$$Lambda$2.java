package com.hotellook.ui.screen.filters;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FilterFragmentSideMenu$$Lambda$2 implements OnClickListener {
    private static final FilterFragmentSideMenu$$Lambda$2 instance;

    static {
        instance = new FilterFragmentSideMenu$$Lambda$2();
    }

    private FilterFragmentSideMenu$$Lambda$2() {
    }

    public static OnClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onClick(View view) {
        FilterFragmentSideMenu.lambda$setUpToolbar$1(view);
    }
}
