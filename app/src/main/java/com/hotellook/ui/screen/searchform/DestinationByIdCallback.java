package com.hotellook.ui.screen.searchform;

import com.hotellook.HotellookApplication;
import com.hotellook.api.callback.CallbackWithTimeout;
import com.hotellook.api.data.DestinationData;
import com.hotellook.core.api.pojo.cityinfo.CityInfo;
import com.hotellook.events.DestinationFoundEvent;
import com.hotellook.events.FindDestinationFailedEvent;
import retrofit.RetrofitError;
import retrofit.client.Response;

class DestinationByIdCallback extends CallbackWithTimeout<CityInfo> {
    private static final long REQUEST_TIMEOUT = 45000;

    public DestinationByIdCallback() {
        super(REQUEST_TIMEOUT);
    }

    public void success(CityInfo cityInfo, Response response) {
        if (!isFinished()) {
            super.success(cityInfo, response);
            if (cityInfo != null) {
                HotellookApplication.eventBus().post(new DestinationFoundEvent(new DestinationData(cityInfo)));
                return;
            }
            HotellookApplication.eventBus().post(new FindDestinationFailedEvent());
        }
    }

    public void failure(RetrofitError error) {
        super.failure(error);
        HotellookApplication.eventBus().post(new FindDestinationFailedEvent());
    }
}
