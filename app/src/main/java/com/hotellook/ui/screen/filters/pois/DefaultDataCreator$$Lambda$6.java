package com.hotellook.ui.screen.filters.pois;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultDataCreator$$Lambda$6 implements Action1 {
    private final DefaultDataCreator arg$1;

    private DefaultDataCreator$$Lambda$6(DefaultDataCreator defaultDataCreator) {
        this.arg$1 = defaultDataCreator;
    }

    public static Action1 lambdaFactory$(DefaultDataCreator defaultDataCreator) {
        return new DefaultDataCreator$$Lambda$6(defaultDataCreator);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$prepareOtherPois$5((List) obj);
    }
}
