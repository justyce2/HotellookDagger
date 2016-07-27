package com.hotellook.ui.screen.filters.pois;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PoiPickerFragment$$Lambda$6 implements OnEditorActionListener {
    private final PoiPickerFragment arg$1;

    private PoiPickerFragment$$Lambda$6(PoiPickerFragment poiPickerFragment) {
        this.arg$1 = poiPickerFragment;
    }

    public static OnEditorActionListener lambdaFactory$(PoiPickerFragment poiPickerFragment) {
        return new PoiPickerFragment$$Lambda$6(poiPickerFragment);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$setUpSearch$5(textView, i, keyEvent);
    }
}
