package com.hotellook.api.exchange;

public class CurrencyConverter {
    private static final double UNDEFINED = -1.0d;
    private double rate;

    public CurrencyConverter() {
        this.rate = UNDEFINED;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isInitted() {
        return this.rate != UNDEFINED;
    }

    public double convert(double value) {
        return this.rate * value;
    }

    public void reset() {
        this.rate = UNDEFINED;
    }
}
