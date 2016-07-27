package com.hotellook.ui.screen.searchform;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$1 implements OnClickListener {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$1(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static OnClickListener lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$1(searchFormFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}
