package com.hotellook.search;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Highlights {
    private final Map<Long, Map<Integer, Map<Integer, String>>> highlights;

    public Highlights(Map<Long, Map<Integer, Map<Integer, String>>> highlights) {
        if (highlights == null) {
            highlights = Collections.emptyMap();
        }
        this.highlights = highlights;
    }

    @NonNull
    public HighlightsByRooms get(long id) {
        Map<Integer, Map<Integer, String>> highlightsByHotel = (Map) this.highlights.get(Long.valueOf(id));
        if (highlightsByHotel == null) {
            return new HighlightsByRooms(Collections.emptyMap());
        }
        return new HighlightsByRooms(highlightsByHotel);
    }

    @NonNull
    public HighlightsByRooms get(Iterable<Long> ids) {
        Map<Integer, Map<Integer, String>> result = new HashMap();
        for (Long hotelId : ids) {
            Map<Integer, Map<Integer, String>> nextHighlights = (Map) this.highlights.get(hotelId);
            if (nextHighlights != null) {
                result.putAll(nextHighlights);
            }
        }
        return new HighlightsByRooms(result);
    }
}
