package com.hotellook.utils;

import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;

public class NetworkUtil {
    public static boolean isRetrofitNetworkError(Throwable error) {
        return (error instanceof RetrofitError) && ((RetrofitError) error).getKind() == Kind.NETWORK;
    }
}
