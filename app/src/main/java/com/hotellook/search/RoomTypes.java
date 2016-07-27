package com.hotellook.search;

import java.util.Map;

public class RoomTypes {
    private final Map<Integer, String> roomTypes;

    public RoomTypes(Map<Integer, String> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public String toString() {
        return "roomTypes=" + this.roomTypes + '}';
    }

    public Map<Integer, String> all() {
        return this.roomTypes;
    }
}
