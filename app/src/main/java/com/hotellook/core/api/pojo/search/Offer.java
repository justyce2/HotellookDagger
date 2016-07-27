package com.hotellook.core.api.pojo.search;

import com.google.gson.annotations.Expose;

public class Offer {
    @Expose
    private int gateId;
    @Expose
    private String gateName;
    @Expose
    private Integer internalTypeId;
    @Expose
    private String name;
    @Expose
    private OptionsData options;
    @Expose
    private double price;
    @Expose
    private double priceUsd;
    @Expose
    private int roomId;

    public Integer getInternalTypeId() {
        return this.internalTypeId;
    }

    public OptionsData getOptions() {
        return this.options;
    }

    public String getGateName() {
        return this.gateName;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public double getPriceUsd() {
        return this.priceUsd;
    }

    public int getGateId() {
        return this.gateId;
    }

    public int getRoomId() {
        return this.roomId;
    }
}
