package com.hotellook.ui.view.bottomnavigation;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BottomNavigationView$$Lambda$1 implements OnClickListener {
    private final BottomNavigationView arg$1;
    private final Item arg$2;

    private BottomNavigationView$$Lambda$1(BottomNavigationView bottomNavigationView, Item item) {
        this.arg$1 = bottomNavigationView;
        this.arg$2 = item;
    }

    public static OnClickListener lambdaFactory$(BottomNavigationView bottomNavigationView, Item item) {
        return new BottomNavigationView$$Lambda$1(bottomNavigationView, item);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$createItemView$0(this.arg$2, view);
    }
}
