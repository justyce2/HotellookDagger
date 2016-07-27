package com.hotellook.ui.screen.common;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Observable;
import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

final /* synthetic */ class BaseFragment$$Lambda$1 implements Transformer {
    private static final BaseFragment$$Lambda$1 instance;

    static {
        instance = new BaseFragment$$Lambda$1();
    }

    private BaseFragment$$Lambda$1() {
    }

    public static Transformer lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((Observable) obj).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
