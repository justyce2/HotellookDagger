package com.hotellook.statistics.mixpanel;

import com.hotellook.filters.items.SortingItem;
import com.hotellook.statistics.Constants.MixPanelParams;
import com.hotellook.statistics.MixPanelEventsKeeper;
import java.util.HashMap;
import java.util.Map;

public class SortingChangedParams implements MixPanelParamsBuilder {
    public static final String UNKNOWN = "unknown";
    private final MixPanelEventsKeeper eventsKeeper;
    private final SortingItem sortingItem;

    public SortingChangedParams(SortingItem sortingItem, MixPanelEventsKeeper eventsKeeper) {
        this.sortingItem = sortingItem;
        this.eventsKeeper = eventsKeeper;
    }

    public Map<String, Object> buildParams() {
        String sortType = new SortingTypeConverter(this.sortingItem.getAlgoId(), this.sortingItem.getDistanceTarget()).convert();
        Map<String, Object> params = new HashMap();
        params.put(MixPanelParams.SORT_TYPE, sortType);
        params.put(MixPanelParams.SORT_HOTELS_BEFORE, Integer.valueOf(this.eventsKeeper.getHotelsShowedCountBeforeSortingAndResetIt()));
        return params;
    }
}
