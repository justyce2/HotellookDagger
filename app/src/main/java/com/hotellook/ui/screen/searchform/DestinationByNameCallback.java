package com.hotellook.ui.screen.searchform;

import android.support.annotation.Nullable;
import com.hotellook.HotellookApplication;
import com.hotellook.api.callback.CallbackWithTimeout;
import com.hotellook.api.data.DestinationData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteCityData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteData;
import com.hotellook.core.api.pojo.autocomlete.AutocompleteHotelData;
import com.hotellook.events.DestinationFoundEvent;
import com.hotellook.events.FindDestinationFailedEvent;
import java.util.List;
import retrofit.RetrofitError;
import retrofit.client.Response;

class DestinationByNameCallback extends CallbackWithTimeout<AutocompleteData> {
    private static final long REQUEST_TIMEOUT = 45000;
    private final String name;

    public DestinationByNameCallback(String name) {
        super(REQUEST_TIMEOUT);
        this.name = name;
    }

    public void success(AutocompleteData autocompleteData, Response response) {
        if (!isFinished()) {
            super.success(autocompleteData, response);
            if (autocompleteData != null) {
                DestinationData destination = extractCityByIata(this.name, autocompleteData.getCities());
                if (destination == null) {
                    if (autocompleteData.getCities() != null && autocompleteData.getCities().size() > 0) {
                        destination = new DestinationData((AutocompleteCityData) autocompleteData.getCities().get(0));
                    }
                    if (destination == null && autocompleteData.getHotels() != null && autocompleteData.getHotels().size() > 0) {
                        destination = new DestinationData((AutocompleteHotelData) autocompleteData.getHotels().get(0));
                    }
                }
                if (destination != null) {
                    HotellookApplication.eventBus().post(new DestinationFoundEvent(destination));
                    return;
                }
            }
            HotellookApplication.eventBus().post(new FindDestinationFailedEvent());
        }
    }

    public void failure(RetrofitError error) {
        super.failure(error);
        HotellookApplication.eventBus().post(new FindDestinationFailedEvent());
    }

    @Nullable
    private DestinationData extractCityByIata(String sourceIata, List<AutocompleteCityData> cities) {
        if (cities != null) {
            for (AutocompleteCityData city : cities) {
                for (String iata : city.getIata()) {
                    if (sourceIata.equals(iata)) {
                        return new DestinationData(city);
                    }
                }
            }
        }
        return null;
    }
}
