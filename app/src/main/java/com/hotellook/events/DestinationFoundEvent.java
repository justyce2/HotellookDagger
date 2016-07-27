package com.hotellook.events;

import com.hotellook.api.data.DestinationData;

public class DestinationFoundEvent {
    public final DestinationData data;

    public DestinationFoundEvent(DestinationData data) {
        this.data = data;
    }
}
