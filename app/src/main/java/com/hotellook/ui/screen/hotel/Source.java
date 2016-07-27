package com.hotellook.ui.screen.hotel;

import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.mixpanel.LaunchSource;

public enum Source {
    CITY_SEARCH_RESULTS(1, MixPanelParams.ALL),
    BEST_HOTELS(2, MixPanelParams.BEST),
    FAVORITES(3, MixPanelParams.SUPER_FAVORITES),
    FAVORITES_SEARCH_RESULTS(3, MixPanelParams.MINE),
    CITY_SEARCH_MAP(4, MixPanelParams.MAP),
    HOTEL_SEARCH(5, MixPanelParams.FORM),
    INTENT(6, LaunchSource.DEEPLINK);
    
    public final int id;
    public final String mixpanelName;

    private Source(int id, String mixpanelName) {
        this.id = id;
        this.mixpanelName = mixpanelName;
    }

    public boolean isCitySearch() {
        return this == CITY_SEARCH_MAP || this == CITY_SEARCH_RESULTS;
    }
}
