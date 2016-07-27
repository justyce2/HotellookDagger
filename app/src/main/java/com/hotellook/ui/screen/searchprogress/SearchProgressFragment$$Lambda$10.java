package com.hotellook.ui.screen.searchprogress;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.KeyEvent;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchProgressFragment$$Lambda$10 implements OnKeyListener {
    private final SearchProgressFragment arg$1;

    private SearchProgressFragment$$Lambda$10(SearchProgressFragment searchProgressFragment) {
        this.arg$1 = searchProgressFragment;
    }

    public static OnKeyListener lambdaFactory$(SearchProgressFragment searchProgressFragment) {
        return new SearchProgressFragment$$Lambda$10(searchProgressFragment);
    }

    @Hidden
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$cancelSearchWithDialog$9(dialogInterface, i, keyEvent);
    }
}
