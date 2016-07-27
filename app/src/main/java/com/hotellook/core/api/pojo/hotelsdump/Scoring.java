package com.hotellook.core.api.pojo.hotelsdump;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scoring {
    @Expose
    private int beach;
    @Expose
    private int business;
    @Expose
    private int romance;
    @SerializedName("with_children")
    @Expose
    private int withChildren;

    public int getBeach() {
        return this.beach;
    }

    public int getRomance() {
        return this.romance;
    }

    public int getBusiness() {
        return this.business;
    }

    public int getWithChildren() {
        return this.withChildren;
    }
}
