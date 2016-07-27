package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class PoiPickerFragment$$Lambda$13 implements Action1 {
    private final PoiPickerFragment arg$1;

    private PoiPickerFragment$$Lambda$13(PoiPickerFragment poiPickerFragment) {
        this.arg$1 = poiPickerFragment;
    }

    public static Action1 lambdaFactory$(PoiPickerFragment poiPickerFragment) {
        return new PoiPickerFragment$$Lambda$13(poiPickerFragment);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onError((Throwable) obj);
    }
}
