package com.hotellook.core.api.pojo.search;

import java.util.List;
import java.util.Map;

public class AsyncSearchResults {
    private int currentResultsCount;
    private Map<Long, Map<Integer, Map<Integer, Discount>>> discountsByRoom;
    private List<Integer> gates;
    private Map<Long, Map<Integer, Map<Integer, String>>> highlights;
    private Map<Long, List<Offer>> hotels;
    private String searchId;
    private boolean stop;

    public Map<Long, List<Offer>> hotels() {
        return this.hotels;
    }

    public String searchId() {
        return this.searchId;
    }

    public List<Integer> gates() {
        return this.gates;
    }

    public int currentResultsCount() {
        return this.currentResultsCount;
    }

    public Map<Long, Map<Integer, Map<Integer, Discount>>> discountsByRoom() {
        return this.discountsByRoom;
    }

    public Map<Long, Map<Integer, Map<Integer, String>>> highlights() {
        return this.highlights;
    }

    public boolean isStop() {
        return this.stop;
    }
}
