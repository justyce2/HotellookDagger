package com.hotellook.ui.screen.filters.hotelname;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotelNameSelectorFragment$$Lambda$1 implements OnEditorActionListener {
    private final HotelNameSelectorFragment arg$1;

    private HotelNameSelectorFragment$$Lambda$1(HotelNameSelectorFragment hotelNameSelectorFragment) {
        this.arg$1 = hotelNameSelectorFragment;
    }

    public static OnEditorActionListener lambdaFactory$(HotelNameSelectorFragment hotelNameSelectorFragment) {
        return new HotelNameSelectorFragment$$Lambda$1(hotelNameSelectorFragment);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$setUpSearch$0(textView, i, keyEvent);
    }
}
