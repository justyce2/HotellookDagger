package com.hotellook.core.api;

import android.content.Context;
import com.hotellook.core.api.utils.Log;
import com.jakewharton.retrofit.Ok3Client;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RequestInterceptor.RequestFacade;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;

public class HotellookClient {
    private static final String API_URL = "https://yasen.hotellook.com/";
    private static final String HEADER_DEVICE_INFO = "Client-Device-Info";
    private static final int MAX_CACHE_FILE_SIZE = 31457280;
    private static volatile HotellookClient instance;
    private final DeviceInfo deviceInfo;
    private ErrorHandler errorHandler;
    private final HotellookService hotellookService;
    private final RestAdapter restAdapter;

    /* renamed from: com.hotellook.core.api.HotellookClient.1 */
    class C11941 implements RequestInterceptor {
        C11941() {
        }

        public void intercept(RequestFacade request) {
            request.addHeader(HotellookClient.HEADER_DEVICE_INFO, HotellookClient.this.getDeviceInfo());
        }
    }

    /* renamed from: com.hotellook.core.api.HotellookClient.2 */
    class C11952 implements ErrorHandler {
        C11952() {
        }

        public Throwable handleError(RetrofitError retrofitError) {
            if (HotellookClient.this.errorHandler != null) {
                return HotellookClient.this.errorHandler.handleError(retrofitError);
            }
            return retrofitError;
        }
    }

    private HotellookClient(Context context) {
        this.deviceInfo = DeviceInfo.from(context);
        OkHttpClient okHttpClient = null;
        try {
            okHttpClient = createOkHttpClient(context);
        } catch (Exception e) {
            Log.m703e("hotellook core", "Unable to create http cache dir, caching will be skipped");
        }
        Builder builder = new Builder();
        builder.setEndpoint(getHotellookServer());
        builder.setErrorHandler(getErrorHandler());
        builder.setRequestInterceptor(getRequestInterceptor());
        if (okHttpClient != null) {
            builder.setClient(new Ok3Client(okHttpClient));
        }
        this.restAdapter = builder.build();
        this.hotellookService = (HotellookService) this.restAdapter.create(HotellookService.class);
    }

    public static HotellookClient getInstance(Context context) {
        if (instance == null) {
            synchronized (HotellookClient.class) {
                if (instance == null) {
                    instance = new HotellookClient(context);
                }
            }
        }
        return instance;
    }

    public HotellookService getService() {
        return this.hotellookService;
    }

    public String getToken() {
        return this.deviceInfo.token;
    }

    public String getHost() {
        return this.deviceInfo.host;
    }

    public String getDeviceInfo() {
        return this.deviceInfo.toString();
    }

    public HotellookImageUrlProvider getImageUrlProvider() {
        return new HotellookImageUrlProvider();
    }

    private OkHttpClient createOkHttpClient(Context context) throws IOException {
        if (context == null) {
            return null;
        }
        return new OkHttpClient.Builder().cache(new Cache(new File(context.getCacheDir(), "HttpCache"), 31457280)).readTimeout(2, TimeUnit.MINUTES).connectTimeout(2, TimeUnit.MINUTES).build();
    }

    private String getHotellookServer() {
        return API_URL;
    }

    private RequestInterceptor getRequestInterceptor() {
        return new C11941();
    }

    private ErrorHandler getErrorHandler() {
        return new C11952();
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.restAdapter.setLogLevel(logLevel);
    }
}
