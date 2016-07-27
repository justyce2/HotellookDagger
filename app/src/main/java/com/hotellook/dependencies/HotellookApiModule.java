package com.hotellook.dependencies;

import android.app.Application;
import android.os.Build.VERSION;
import com.hotellook.api.ShortUrlService;
import com.hotellook.core.api.HotellookClient;
import com.hotellook.core.api.HotellookImageUrlProvider;
import com.hotellook.core.api.HotellookService;
import com.hotellook.dependencies.qualifier.DeviceInfo;
import com.hotellook.dependencies.qualifier.Host;
import com.hotellook.dependencies.qualifier.Token;
import com.hotellook.utils.EventBus;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

@Module
public class HotellookApiModule {
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_DEVICE_INFO = "Client-Device-Info";

    @Singleton
    @Provides
    public HotellookClient provideHotellookClient(Application app, EventBus bus) {
        HotellookClient client = HotellookClient.getInstance(app);
        client.setErrorHandler(HotellookApiModule$$Lambda$1.lambdaFactory$(bus));
        return client;
    }

    @Singleton
    @Provides
    public HotellookService provideHotellookService(HotellookClient client) {
        return client.getService();
    }

    @Singleton
    @Token
    @Provides
    public String provideToken(HotellookClient client) {
        return client.getToken();
    }

    @Singleton
    @Host
    @Provides
    public String provideHost(HotellookClient client) {
        return client.getHost();
    }

    @Singleton
    @DeviceInfo
    @Provides
    public String provideDeviceInfo(HotellookClient client) {
        return client.getDeviceInfo();
    }

    @Singleton
    @Provides
    public Interceptor provideApiInterceptor(HotellookClient client) {
        return HotellookApiModule$$Lambda$2.lambdaFactory$(client);
    }

    static /* synthetic */ Response lambda$provideApiInterceptor$1(HotellookClient client, Chain chain) throws IOException {
        Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader(HEADER_DEVICE_INFO, client.getDeviceInfo());
        if (VERSION.SDK_INT >= 19) {
            requestBuilder.addHeader(HEADER_ACCEPT, "image/webp");
        }
        return chain.proceed(requestBuilder.build());
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient(Interceptor apiInterceptor) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addNetworkInterceptor(apiInterceptor);
        return clientBuilder.build();
    }

    @Singleton
    @Provides
    public HotellookImageUrlProvider provideHotellookImageUrlProvider() {
        return new HotellookImageUrlProvider();
    }

    @Singleton
    @Provides
    public ShortUrlService provideInternalHotellookService() {
        return (ShortUrlService) new RestAdapter.Builder().setEndpoint(ShortUrlService.ENDPOINT).setLogLevel(LogLevel.NONE).build().create(ShortUrlService.class);
    }
}
