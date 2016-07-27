package com.hotellook.ui.screen.filters.pois;

import com.hotellook.ui.view.TouchyRecyclerView.OnNoChildClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PoiPickerFragment$$Lambda$1 implements OnNoChildClickListener {
    private final PoiPickerFragment arg$1;

    private PoiPickerFragment$$Lambda$1(PoiPickerFragment poiPickerFragment) {
        this.arg$1 = poiPickerFragment;
    }

    public static OnNoChildClickListener lambdaFactory$(PoiPickerFragment poiPickerFragment) {
        return new PoiPickerFragment$$Lambda$1(poiPickerFragment);
    }

    @Hidden
    public void onNoChildClick() {
        this.arg$1.close();
    }
}
