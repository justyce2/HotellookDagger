package com.hotellook.api;

import com.hotellook.api.data.ShortUrlData;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ShortUrlService {
    public static final String ENDPOINT = "http://htl.io/";

    @GET("/yourls-api.php")
    Observable<ShortUrlData> observeShortUrl(@Query("username") String str, @Query("password") String str2, @Query("action") String str3, @Query("format") String str4, @Query("url") String str5);
}
