package com.hotellook.events;

import retrofit.RetrofitError;

public class HotellookClientErrorEvent {
    public final RetrofitError error;

    public HotellookClientErrorEvent(RetrofitError error) {
        this.error = error;
    }
}
