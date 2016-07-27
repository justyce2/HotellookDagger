package com.hotellook.ui.view.calendar;

import com.hotellook.ui.view.calendar.CalendarPickerView.OnRangeSelectedListener;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.Date;

final /* synthetic */ class CalendarPickerView$$Lambda$1 implements OnRangeSelectedListener {
    private static final CalendarPickerView$$Lambda$1 instance;

    static {
        instance = new CalendarPickerView$$Lambda$1();
    }

    private CalendarPickerView$$Lambda$1() {
    }

    public static OnRangeSelectedListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onRangeSelected(Date date, Date date2, boolean z) {
        CalendarPickerView.lambda$new$0(date, date2, z);
    }
}
