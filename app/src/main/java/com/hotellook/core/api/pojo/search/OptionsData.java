package com.hotellook.core.api.pojo.search;

import com.google.gson.annotations.Expose;

public class OptionsData {
    @Expose
    private int available;
    @Expose
    private boolean breakfast;
    @Expose
    private Boolean cardRequired;
    @Expose
    private Boolean deposit;
    @Expose
    private boolean freeWifi;
    @Expose
    private boolean hotelWebsite;
    @Expose
    private boolean privatePrice;
    @Expose
    private boolean refundable;
    @Expose
    private boolean smoking;
    @Expose
    private String view;
    @Expose
    private String viewSentence;

    public int getAvailableRooms() {
        return this.available;
    }

    public boolean isSmoking() {
        return this.smoking;
    }

    public boolean hasBreakfast() {
        return this.breakfast;
    }

    public boolean isCardNotRequired() {
        return (this.cardRequired == null || this.cardRequired.booleanValue()) ? false : true;
    }

    public boolean isRefundable() {
        return this.refundable;
    }

    public boolean hasPaymentInfo() {
        return this.deposit != null;
    }

    public boolean isPaymentNow() {
        return this.deposit.booleanValue();
    }

    public boolean isPaymentInHotel() {
        return !this.deposit.booleanValue();
    }

    public boolean isFreeWifi() {
        return this.freeWifi;
    }

    public boolean hasHotelWebsite() {
        return this.hotelWebsite;
    }

    public int getOptionsAmount() {
        int amount = 0;
        if (this.smoking) {
            amount = 0 + 1;
        }
        if (this.breakfast) {
            amount++;
        }
        if (this.refundable) {
            amount++;
        }
        if (this.freeWifi) {
            return amount + 1;
        }
        return amount;
    }

    public boolean hasPrivatePrice() {
        return this.privatePrice;
    }

    public String getView() {
        return this.view;
    }

    public String getViewSentence() {
        return this.viewSentence;
    }
}
