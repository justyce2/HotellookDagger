package com.hotellook.ui.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormView$$Lambda$1 implements OnTouchListener {
    private static final SearchFormView$$Lambda$1 instance;

    static {
        instance = new SearchFormView$$Lambda$1();
    }

    private SearchFormView$$Lambda$1() {
    }

    public static OnTouchListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
