package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Map;
import rx.functions.Func1;

final /* synthetic */ class RoomTypesSource$$Lambda$1 implements Func1 {
    private static final RoomTypesSource$$Lambda$1 instance;

    static {
        instance = new RoomTypesSource$$Lambda$1();
    }

    private RoomTypesSource$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new RoomTypes((Map) obj);
    }
}
