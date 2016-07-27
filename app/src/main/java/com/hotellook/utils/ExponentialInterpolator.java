package com.hotellook.utils;

public class ExponentialInterpolator {
    private final double maxValue;
    private final double minValue;

    public ExponentialInterpolator(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getInterpolation(double value) {
        return this.minValue + ((this.maxValue - this.minValue) * (Math.exp(Math.pow((value - this.minValue) / (this.maxValue - this.minValue), 3.0d) * 0.7d) - 1.0d));
    }

    public double backInterpolate(double value) {
        return this.minValue + ((this.maxValue - this.minValue) * Math.pow(Math.log(1.0d + ((value - this.minValue) / (this.maxValue - this.minValue))) / 0.7d, 0.3333333333333333d));
    }
}
