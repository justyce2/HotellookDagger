package com.hotellook.ui.screen.filters.pois;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PoiPickerFragment$$Lambda$4 implements OnTouchListener {
    private final PoiPickerFragment arg$1;

    private PoiPickerFragment$$Lambda$4(PoiPickerFragment poiPickerFragment) {
        this.arg$1 = poiPickerFragment;
    }

    public static OnTouchListener lambdaFactory$(PoiPickerFragment poiPickerFragment) {
        return new PoiPickerFragment$$Lambda$4(poiPickerFragment);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$setUpKeyboardHiderOverlay$2(view, motionEvent);
    }
}
