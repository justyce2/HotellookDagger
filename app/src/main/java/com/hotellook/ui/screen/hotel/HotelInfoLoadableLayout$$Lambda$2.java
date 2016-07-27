package com.hotellook.ui.screen.hotel;

import com.hotellook.ui.screen.hotel.HotelInfoLoadableLayout.LCE;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class HotelInfoLoadableLayout$$Lambda$2 implements Func1 {
    private static final HotelInfoLoadableLayout$$Lambda$2 instance;

    static {
        instance = new HotelInfoLoadableLayout$$Lambda$2();
    }

    private HotelInfoLoadableLayout$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return LCE.CONTENT;
    }
}
