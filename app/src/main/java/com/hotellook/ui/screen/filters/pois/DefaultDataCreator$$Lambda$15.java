package com.hotellook.ui.screen.filters.pois;

import com.hotellook.db.data.PoiHistoryItem;
import com.hotellook.ui.screen.filters.pois.listitems.PoiHistoryItemBinder;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultDataCreator$$Lambda$15 implements Func1 {
    private static final DefaultDataCreator$$Lambda$15 instance;

    static {
        instance = new DefaultDataCreator$$Lambda$15();
    }

    private DefaultDataCreator$$Lambda$15() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new PoiHistoryItemBinder((PoiHistoryItem) obj);
    }
}
