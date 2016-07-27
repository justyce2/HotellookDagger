package com.hotellook.ui.screen;

import com.hotellook.ui.view.calendar.CalendarPickerView.OnRangeSelectedListener;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Date;

final /* synthetic */ class DatesPickerFragment$$Lambda$1 implements OnRangeSelectedListener {
    private final DatesPickerFragment arg$1;

    private DatesPickerFragment$$Lambda$1(DatesPickerFragment datesPickerFragment) {
        this.arg$1 = datesPickerFragment;
    }

    public static OnRangeSelectedListener lambdaFactory$(DatesPickerFragment datesPickerFragment) {
        return new DatesPickerFragment$$Lambda$1(datesPickerFragment);
    }

    @Hidden
    public void onRangeSelected(Date date, Date date2, boolean z) {
        this.arg$1.lambda$onCreateView$3(date, date2, z);
    }
}
