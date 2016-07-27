package com.hotellook.core.api.pojo.minprice;

import com.google.gson.annotations.Expose;

public class MinPriceData {
    @Expose
    private double price;
    @Expose
    private int rate;

    public int getRate() {
        return this.rate;
    }

    public double getPrice() {
        return this.price;
    }
}
