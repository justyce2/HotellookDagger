package com.hotellook.ui.screen.filters.pois;

import com.hotellook.ui.screen.filters.pois.listitems.SeasonLocationItemBinder;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$3 implements Func1 {
    private final String arg$1;
    private final String arg$2;

    private DefaultDataCreator$$Lambda$3(String str, String str2) {
        this.arg$1 = str;
        this.arg$2 = str2;
    }

    public static Func1 lambdaFactory$(String str, String str2) {
        return new DefaultDataCreator$$Lambda$3(str, str2);
    }

    @Hidden
    public Object call(Object obj) {
        return new SeasonLocationItemBinder((List) obj, this.arg$1, this.arg$2);
    }
}
