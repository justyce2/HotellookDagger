package com.hotellook.ui.screen.common;

import android.view.MenuItem;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BaseFragment$$Lambda$3 implements Runnable {
    private final BaseFragment arg$1;
    private final MenuItem[] arg$2;

    private BaseFragment$$Lambda$3(BaseFragment baseFragment, MenuItem[] menuItemArr) {
        this.arg$1 = baseFragment;
        this.arg$2 = menuItemArr;
    }

    public static Runnable lambdaFactory$(BaseFragment baseFragment, MenuItem[] menuItemArr) {
        return new BaseFragment$$Lambda$3(baseFragment, menuItemArr);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$showMenuItemsWithDelay$3(this.arg$2);
    }
}
