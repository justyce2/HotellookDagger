package com.hotellook.ui.screen.filters.pois;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PoiPickerFragment$$Lambda$5 implements OnFocusChangeListener {
    private final PoiPickerFragment arg$1;

    private PoiPickerFragment$$Lambda$5(PoiPickerFragment poiPickerFragment) {
        this.arg$1 = poiPickerFragment;
    }

    public static OnFocusChangeListener lambdaFactory$(PoiPickerFragment poiPickerFragment) {
        return new PoiPickerFragment$$Lambda$5(poiPickerFragment);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$setUpSearch$4(view, z);
    }
}
