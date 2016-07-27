package com.hotellook.filters;

import com.hotellook.filters.task.FilterTask.Callback;
import com.hotellook.filters.task.FilterTask.FilteredData;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class Filters$$Lambda$1 implements Callback {
    private final Filters arg$1;
    private final Object arg$2;

    private Filters$$Lambda$1(Filters filters, Object obj) {
        this.arg$1 = filters;
        this.arg$2 = obj;
    }

    public static Callback lambdaFactory$(Filters filters, Object obj) {
        return new Filters$$Lambda$1(filters, obj);
    }

    @Hidden
    public void onFiltered(FilteredData filteredData) {
        this.arg$1.lambda$prepareFilterTask$0(this.arg$2, filteredData);
    }
}
