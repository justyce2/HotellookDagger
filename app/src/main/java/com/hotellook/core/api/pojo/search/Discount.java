package com.hotellook.core.api.pojo.search;

import com.google.gson.annotations.SerializedName;

public class Discount {
    @SerializedName("change_percentage")
    private float changePercentage;
    @SerializedName("old_price")
    private double oldPrice;

    public float getChangePercentage() {
        return this.changePercentage;
    }

    public double getOldPrice() {
        return this.oldPrice;
    }
}
