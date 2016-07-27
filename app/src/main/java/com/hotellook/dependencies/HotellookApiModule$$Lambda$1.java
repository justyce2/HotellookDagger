package com.hotellook.dependencies;

import com.hotellook.events.HotellookClientErrorEvent;
import com.hotellook.utils.EventBus;
import java.lang.invoke.LambdaForm.Hidden;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

final /* synthetic */ class HotellookApiModule$$Lambda$1 implements ErrorHandler {
    private final EventBus arg$1;

    private HotellookApiModule$$Lambda$1(EventBus eventBus) {
        this.arg$1 = eventBus;
    }

    public static ErrorHandler lambdaFactory$(EventBus eventBus) {
        return new HotellookApiModule$$Lambda$1(eventBus);
    }

    @Hidden
    public Throwable handleError(RetrofitError retrofitError) {
        return this.arg$1.post(new HotellookClientErrorEvent(retrofitError));
    }
}
