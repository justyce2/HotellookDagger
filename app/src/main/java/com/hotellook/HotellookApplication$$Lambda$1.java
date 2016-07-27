package com.hotellook;

import com.facebook.common.soloader.SoLoaderShim.Handler;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HotellookApplication$$Lambda$1 implements Handler {
    private final HotellookApplication arg$1;

    private HotellookApplication$$Lambda$1(HotellookApplication hotellookApplication) {
        this.arg$1 = hotellookApplication;
    }

    public static Handler lambdaFactory$(HotellookApplication hotellookApplication) {
        return new HotellookApplication$$Lambda$1(hotellookApplication);
    }

    @Hidden
    public void loadLibrary(String str) {
        this.arg$1.lambda$initFresco$0(str);
    }
}
