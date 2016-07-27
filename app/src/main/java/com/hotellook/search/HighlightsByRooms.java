package com.hotellook.search;

import com.hotellook.core.api.pojo.search.Offer;
import java.util.HashMap;
import java.util.Map;

public class HighlightsByRooms {
    public static final String NONE = "none";
    private final Map<Integer, Map<Integer, String>> highlights;

    public HighlightsByRooms(Map<Integer, Map<Integer, String>> highlights) {
        this.highlights = highlights;
    }

    public String highlight(Offer offer) {
        Map<Integer, String> highlightsByGate = (Map) this.highlights.get(Integer.valueOf(offer.getGateId()));
        if (highlightsByGate == null) {
            return NONE;
        }
        String highightByRoom = (String) highlightsByGate.get(Integer.valueOf(offer.getRoomId()));
        return highightByRoom == null ? NONE : highightByRoom;
    }

    public HighlightsByRooms merged(HighlightsByRooms other) {
        Map<Integer, Map<Integer, String>> mergedHighlights = new HashMap();
        mergedHighlights.putAll(this.highlights);
        mergedHighlights.putAll(other.highlights);
        return new HighlightsByRooms(mergedHighlights);
    }
}
