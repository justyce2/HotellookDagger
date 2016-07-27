package com.hotellook.events;

import retrofit.RetrofitError;

public class NetworkFailEvent {
    public final RetrofitError error;

    public NetworkFailEvent(RetrofitError error) {
        this.error = error;
    }
}
