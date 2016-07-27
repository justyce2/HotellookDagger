package com.hotellook.api.dataloaders;

import android.support.annotation.NonNull;
import com.hotellook.HotellookApplication;
import com.hotellook.core.api.pojo.common.Coordinates;
import com.hotellook.events.OnStreetViewCheckFailedEvent;
import com.hotellook.events.OnStreetViewCheckLoadedEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class StreetViewCheckLoader extends BaseLoader {
    public static final String URL = "http://maps.google.com/cbk?output=json&hl=en&ll={LAT},{LON}&radius={RAD}&cb_client=maps_sv&v=4";
    private boolean mHasStreetView;
    private final OkHttpClient mOkHttpClient;

    /* renamed from: com.hotellook.api.dataloaders.StreetViewCheckLoader.1 */
    class C11821 implements Callback {
        C11821() {
        }

        public void onFailure(Call call, IOException e) {
            StreetViewCheckLoader.this.state = State.FAILED;
            HotellookApplication.eventBus().post(new OnStreetViewCheckFailedEvent());
        }

        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                StreetViewCheckLoader.this.state = State.LOADED;
                try {
                    if (response.body().string().length() > 2) {
                        StreetViewCheckLoader.this.mHasStreetView = true;
                    }
                } catch (IOException e) {
                }
                HotellookApplication.eventBus().post(new OnStreetViewCheckLoadedEvent(StreetViewCheckLoader.this.mHasStreetView));
                return;
            }
            onFailure(null, null);
        }
    }

    public StreetViewCheckLoader(@NonNull OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient.newBuilder().connectTimeout(2000, TimeUnit.MILLISECONDS).readTimeout(2000, TimeUnit.MILLISECONDS).build();
    }

    public void load(Coordinates location, int radius) {
        this.state = State.LOADING;
        this.mOkHttpClient.newCall(new Builder().url(URL.replace("{LAT}", String.valueOf(location.getLat())).replace("{LON}", String.valueOf(location.getLon())).replace("{RAD}", String.valueOf(radius))).build()).enqueue(new C11821());
    }

    public boolean hasStreetView() {
        return this.mHasStreetView;
    }

    public boolean isLoading() {
        return this.state == State.LOADING;
    }

    public boolean isLoaded() {
        return this.state == State.FAILED || this.state == State.LOADED;
    }

    public boolean hasFailed() {
        return this.state == State.FAILED;
    }

    public void reset() {
        this.state = State.IDLE;
        this.mHasStreetView = false;
    }
}
