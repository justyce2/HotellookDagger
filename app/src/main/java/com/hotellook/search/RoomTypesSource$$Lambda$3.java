package com.hotellook.search;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class RoomTypesSource$$Lambda$3 implements Action1 {
    private final RoomTypesSource arg$1;

    private RoomTypesSource$$Lambda$3(RoomTypesSource roomTypesSource) {
        this.arg$1 = roomTypesSource;
    }

    public static Action1 lambdaFactory$(RoomTypesSource roomTypesSource) {
        return new RoomTypesSource$$Lambda$3(roomTypesSource);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.logError((Throwable) obj);
    }
}
