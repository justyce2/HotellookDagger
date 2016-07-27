package com.hotellook.statistics.mixpanel;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.utils.Connectivity;
import java.util.HashMap;
import java.util.Map;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ConnectionErrorParams implements MixPanelParamsBuilder {
    @Nullable
    private final RetrofitError error;
    private final String method;

    public ConnectionErrorParams(@Nullable RetrofitError error, String method) {
        this.error = error;
        this.method = method;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        Response response = this.error != null ? this.error.getResponse() : null;
        if (response != null) {
            params.put(MixPanelParams.ERROR_CODE, Integer.valueOf(response.getStatus()));
            params.put(MixPanelParams.SERVER_HEADERS, response.getHeaders());
        }
        params.put(MixPanelParams.ERROR_REFERRER, this.method);
        params.put(MixPanelParams.CONNECTION_TYPE, Connectivity.getNetwork(HotellookApplication.getApp()));
        if (this.error != null) {
            params.put(MixPanelParams.URL, this.error.getUrl());
        }
        return params;
    }
}
