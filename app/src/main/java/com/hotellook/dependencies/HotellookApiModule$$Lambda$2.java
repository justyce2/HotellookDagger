package com.hotellook.dependencies;

import com.hotellook.core.api.HotellookClient;
import java.lang.invoke.LambdaForm.Hidden;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.Response;

final /* synthetic */ class HotellookApiModule$$Lambda$2 implements Interceptor {
    private final HotellookClient arg$1;

    private HotellookApiModule$$Lambda$2(HotellookClient hotellookClient) {
        this.arg$1 = hotellookClient;
    }

    public static Interceptor lambdaFactory$(HotellookClient hotellookClient) {
        return new HotellookApiModule$$Lambda$2(hotellookClient);
    }

    @Hidden
    public Response intercept(Chain chain) {
        return HotellookApiModule.lambda$provideApiInterceptor$1(this.arg$1, chain);
    }
}
