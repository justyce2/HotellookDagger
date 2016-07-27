package com.hotellook.events;

import retrofit.RetrofitError;

public class BaseRetrofitErrorEvent {
    public final RetrofitError error;

    public BaseRetrofitErrorEvent(RetrofitError error) {
        this.error = error;
    }
}
