package com.hotellook.events;

import com.hotellook.core.api.pojo.common.Poi;
import java.util.List;

public class PoisMatchedEvent {
    public final List<Poi> pois;

    public PoisMatchedEvent(List<Poi> pois) {
        this.pois = pois;
    }
}
