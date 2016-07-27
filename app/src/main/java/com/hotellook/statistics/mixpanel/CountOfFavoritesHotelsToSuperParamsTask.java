package com.hotellook.statistics.mixpanel;

import com.hotellook.HotellookApplication;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.SingleParams;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import java.sql.SQLException;

public class CountOfFavoritesHotelsToSuperParamsTask implements Runnable {
    private final MixpanelAPI mixpanelAPI;

    public CountOfFavoritesHotelsToSuperParamsTask(MixpanelAPI mixpanelAPI) {
        this.mixpanelAPI = mixpanelAPI;
    }

    public void run() {
        long favoritesCount = getFavoritesCount();
        this.mixpanelAPI.registerSuperPropertiesMap(new SingleParams(MixPanelParams.SUPER_FAVORITES, Long.valueOf(favoritesCount)).buildParams());
        this.mixpanelAPI.getPeople().set(MixPanelParams.SUPER_FAVORITES, Long.valueOf(favoritesCount));
    }

    private long getFavoritesCount() {
        try {
            return HotellookApplication.getApp().getComponent().getDatabaseHelper().getFavoriteHotelDataDao().countOf();
        } catch (SQLException e) {
            return 0;
        }
    }
}
