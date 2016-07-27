package com.hotellook.core.api.pojo.deeplink;

import com.google.gson.annotations.Expose;

public class DeeplinkData {
    @Expose
    private String deeplink;
    @Expose
    private String gateName;
    @Expose
    private double price;

    public String getDeeplink() {
        return this.deeplink;
    }

    public double getPrice() {
        return this.price;
    }

    public String getGateName() {
        return this.gateName;
    }
}
