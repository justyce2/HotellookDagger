package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultDataCreator$$Lambda$22 implements Action1 {
    private final String arg$1;

    private DefaultDataCreator$$Lambda$22(String str) {
        this.arg$1 = str;
    }

    public static Action1 lambdaFactory$(String str) {
        return new DefaultDataCreator$$Lambda$22(str);
    }

    @Hidden
    public void call(Object obj) {
        DefaultDataCreator.lambda$prepareSection$15(this.arg$1, (List) obj);
    }
}
