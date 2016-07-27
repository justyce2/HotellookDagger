package com.hotellook.statistics.mixpanel;

import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.MixPanelEventsKeeper;
import java.util.HashMap;
import java.util.Map;

public class HotelClosePrams implements MixPanelParamsBuilder {
    private final MixPanelEventsKeeper eventsKeeper;
    private final long hotelId;

    public HotelClosePrams(long hotelId, MixPanelEventsKeeper eventsKeeper) {
        this.hotelId = hotelId;
        this.eventsKeeper = eventsKeeper;
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.HOTEL_ID, Long.valueOf(this.hotelId));
        params.put(MixPanelParams.HOTEL_MAP_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 7)));
        params.put(MixPanelParams.HOTEL_REVIEWS_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 13)));
        params.put(MixPanelParams.HOTEL_RATING_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 12)));
        params.put(MixPanelParams.HOTEL_RATES_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 11)));
        params.put(MixPanelParams.HOTEL_GALLERY_VIEWED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 0)));
        params.put(MixPanelParams.HOTEL_FRAMED, Boolean.valueOf(this.eventsKeeper.isActionMet(this.hotelId, 17)));
        params.put(MixPanelParams.HOTEL_PHOTOS, Integer.valueOf(this.eventsKeeper.getShowedImagesCount(this.hotelId)));
        clearHotelActions();
        return params;
    }

    private void clearHotelActions() {
        this.eventsKeeper.clearAction(this.hotelId, 7);
        this.eventsKeeper.clearAction(this.hotelId, 13);
        this.eventsKeeper.clearAction(this.hotelId, 12);
        this.eventsKeeper.clearAction(this.hotelId, 11);
        this.eventsKeeper.clearAction(this.hotelId, 0);
    }
}
