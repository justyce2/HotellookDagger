package com.hotellook.events;

import com.hotellook.filters.items.HotelNameFilterItem;

public class OpenHotelNameSelectorEvent {
    public final HotelNameFilterItem filter;

    public OpenHotelNameSelectorEvent(HotelNameFilterItem filter) {
        this.filter = filter;
    }
}
