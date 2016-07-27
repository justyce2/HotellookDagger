package com.hotellook.core.api.pojo.search;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchId {
    @SerializedName("gatesList")
    private List<Integer> gates;
    private int gatesCount;
    private List<Integer> gatesToShowUser;
    private List<Integer> officialWebsites;
    @SerializedName("search_id")
    private String searchId;

    public int gatesCount() {
        return this.gatesCount;
    }

    public List<Integer> gates() {
        return this.gates;
    }

    public String searchId() {
        return this.searchId;
    }

    public List<Integer> gatesToShowUser() {
        return this.gatesToShowUser;
    }

    public List<Integer> officialWebsites() {
        return this.officialWebsites;
    }
}
