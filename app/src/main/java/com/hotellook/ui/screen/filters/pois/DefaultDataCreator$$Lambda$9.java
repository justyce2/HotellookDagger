package com.hotellook.ui.screen.filters.pois;

import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.ui.screen.filters.SupportedSeasons;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$9 implements Func1 {
    private final String[] arg$1;
    private final SupportedSeasons arg$2;

    private DefaultDataCreator$$Lambda$9(String[] strArr, SupportedSeasons supportedSeasons) {
        this.arg$1 = strArr;
        this.arg$2 = supportedSeasons;
    }

    public static Func1 lambdaFactory$(String[] strArr, SupportedSeasons supportedSeasons) {
        return new DefaultDataCreator$$Lambda$9(strArr, supportedSeasons);
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultDataCreator.lambda$prepareOtherPoisForCity$6(this.arg$1, this.arg$2, (Poi) obj);
    }
}
