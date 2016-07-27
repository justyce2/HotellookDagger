package com.hotellook.utils;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class EventBus$$Lambda$1 implements Runnable {
    private final EventBus arg$1;
    private final Object arg$2;

    private EventBus$$Lambda$1(EventBus eventBus, Object obj) {
        this.arg$1 = eventBus;
        this.arg$2 = obj;
    }

    public static Runnable lambdaFactory$(EventBus eventBus, Object obj) {
        return new EventBus$$Lambda$1(eventBus, obj);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$post$0(this.arg$2);
    }
}
