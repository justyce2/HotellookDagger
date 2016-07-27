package com.hotellook.rx;

import rx.Subscriber;

public class SubscriberAdapter<T> extends Subscriber<T> {
    public void onCompleted() {
    }

    public void onError(Throwable e) {
    }

    public void onNext(T t) {
    }
}
