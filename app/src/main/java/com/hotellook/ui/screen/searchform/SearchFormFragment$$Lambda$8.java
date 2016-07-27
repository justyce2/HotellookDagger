package com.hotellook.ui.screen.searchform;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchFormFragment$$Lambda$8 implements OnDismissListener {
    private final SearchFormFragment arg$1;

    private SearchFormFragment$$Lambda$8(SearchFormFragment searchFormFragment) {
        this.arg$1 = searchFormFragment;
    }

    public static OnDismissListener lambdaFactory$(SearchFormFragment searchFormFragment) {
        return new SearchFormFragment$$Lambda$8(searchFormFragment);
    }

    @Hidden
    public void onDismiss(DialogInterface dialogInterface) {
        this.arg$1.lambda$showDestinationLoadingDialog$5(dialogInterface);
    }
}
