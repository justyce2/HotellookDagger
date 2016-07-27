package com.hotellook.ui.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormView$$Lambda$2 implements OnTouchListener {
    private final SearchFormView arg$1;

    private SearchFormView$$Lambda$2(SearchFormView searchFormView) {
        this.arg$1 = searchFormView;
    }

    public static OnTouchListener lambdaFactory$(SearchFormView searchFormView) {
        return new SearchFormView$$Lambda$2(searchFormView);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$setUpTouchInterceptor$1(view, motionEvent);
    }
}
