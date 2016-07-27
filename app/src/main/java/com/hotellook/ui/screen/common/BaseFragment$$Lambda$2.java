package com.hotellook.ui.screen.common;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.Observable.Transformer;

final /* synthetic */ class BaseFragment$$Lambda$2 implements Transformer {
    private final BaseFragment arg$1;

    private BaseFragment$$Lambda$2(BaseFragment baseFragment) {
        this.arg$1 = baseFragment;
    }

    public static Transformer lambdaFactory$(BaseFragment baseFragment) {
        return new BaseFragment$$Lambda$2(baseFragment);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$bindUntilDestroyView$1((Observable) obj);
    }
}
