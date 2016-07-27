package com.hotellook.ui.screen;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Date;

final /* synthetic */ class DatesPickerFragment$$Lambda$4 implements Runnable {
    private final DatesPickerFragment arg$1;
    private final Date arg$2;
    private final Date arg$3;

    private DatesPickerFragment$$Lambda$4(DatesPickerFragment datesPickerFragment, Date date, Date date2) {
        this.arg$1 = datesPickerFragment;
        this.arg$2 = date;
        this.arg$3 = date2;
    }

    public static Runnable lambdaFactory$(DatesPickerFragment datesPickerFragment, Date date, Date date2) {
        return new DatesPickerFragment$$Lambda$4(datesPickerFragment, date, date2);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$null$2(this.arg$2, this.arg$3);
    }
}
