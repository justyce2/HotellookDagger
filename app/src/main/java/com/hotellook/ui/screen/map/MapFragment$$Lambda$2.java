package com.hotellook.ui.screen.map;

import com.hotellook.HotellookApplication;
import com.hotellook.events.PoiMapFragmentCloseEvent;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MapFragment$$Lambda$2 implements Runnable {
    private static final MapFragment$$Lambda$2 instance;

    static {
        instance = new MapFragment$$Lambda$2();
    }

    private MapFragment$$Lambda$2() {
    }

    public static Runnable lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void run() {
        HotellookApplication.eventBus().post(new PoiMapFragmentCloseEvent());
    }
}
