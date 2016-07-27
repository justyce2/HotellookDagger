package com.hotellook.filters.task;

import com.hotellook.filters.task.FilterTask.FilteredData;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FilterTask$$Lambda$1 implements Runnable {
    private final FilterTask arg$1;
    private final FilteredData arg$2;

    private FilterTask$$Lambda$1(FilterTask filterTask, FilteredData filteredData) {
        this.arg$1 = filterTask;
        this.arg$2 = filteredData;
    }

    public static Runnable lambdaFactory$(FilterTask filterTask, FilteredData filteredData) {
        return new FilterTask$$Lambda$1(filterTask, filteredData);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$run$0(this.arg$2);
    }
}
