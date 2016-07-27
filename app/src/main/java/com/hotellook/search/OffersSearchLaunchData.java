package com.hotellook.search;

import java.util.List;

public class OffersSearchLaunchData {
    private final List<Integer> gates;
    private final List<Integer> gatesToShowUser;
    private final String searchId;

    public OffersSearchLaunchData(List<Integer> gates, List<Integer> gatesToShowUser, String searchId) {
        this.gates = gates;
        this.gatesToShowUser = gatesToShowUser;
        this.searchId = searchId;
    }

    public List<Integer> gates() {
        return this.gates;
    }

    public List<Integer> gatesToShowUser() {
        return this.gatesToShowUser;
    }

    public String searchId() {
        return this.searchId;
    }
}
