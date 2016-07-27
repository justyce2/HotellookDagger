package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.filters.items.criterion.PriceCriterion;
import com.hotellook.search.SearchData;
import com.hotellook.utils.ExponentialInterpolator;
import java.util.List;

public class PriceFilterItem extends FilterItem<Offer> {
    private static final String SAVE_MAX_POSITION;
    private static final String SAVE_MIN_POSITION;
    private static final String SAVE_TAG;
    private String currency;
    private ExponentialInterpolator exponentialInterpolator;
    private double maxPrice;
    private double maxValue;
    private double minPrice;
    private double minValue;

    public PriceFilterItem() {
        this.minPrice = Double.MAX_VALUE;
        this.maxPrice = Double.MIN_VALUE;
    }

    static {
        SAVE_TAG = PriceFilterItem.class.getSimpleName();
        SAVE_MAX_POSITION = SAVE_TAG + "_min_pos";
        SAVE_MIN_POSITION = SAVE_TAG + "_max_pos";
    }

    @NonNull
    protected Criterion<Offer> getCriterion() {
        return new PriceCriterion(getMinInterpolatedValue(), getMaxInterpolatedValue());
    }

    public void reset() {
        this.minValue = this.minPrice;
        this.maxValue = this.maxPrice;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.currency = searchData.searchParams().currency();
        this.minPrice = Double.MAX_VALUE;
        this.maxPrice = Double.MIN_VALUE;
        for (List<Offer> offers : searchData.offers().all().values()) {
            for (Offer offer : offers) {
                double price = offer.getPrice();
                if (price < this.minPrice) {
                    this.minPrice = price;
                }
                if (price > this.maxPrice) {
                    this.maxPrice = price;
                }
            }
        }
        this.exponentialInterpolator = new ExponentialInterpolator(this.minPrice, this.maxPrice);
        reset();
    }

    public boolean inDefaultState() {
        return this.minValue == this.minPrice && this.maxValue == this.maxPrice;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        long temp = Double.doubleToLongBits(this.minPrice);
        int result = (int) ((temp >>> 32) ^ temp);
        temp = Double.doubleToLongBits(this.maxPrice);
        return (result * 31) + ((int) ((temp >>> 32) ^ temp));
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(SAVE_MAX_POSITION, Double.valueOf(this.maxValue));
        snapshot.addData(SAVE_MIN_POSITION, Double.valueOf(this.minValue));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.maxValue = ((Double) snapshot.getData(SAVE_MAX_POSITION)).doubleValue();
        this.minValue = ((Double) snapshot.getData(SAVE_MIN_POSITION)).doubleValue();
    }

    public boolean hasValidData() {
        return this.maxPrice > this.minPrice;
    }

    public void release() {
    }

    public double getMinInterpolatedValue() {
        return roundValue(this.minValue, this.exponentialInterpolator.getInterpolation(this.minValue));
    }

    public double getMaxInterpolatedValue() {
        return roundValue(this.maxValue, this.exponentialInterpolator.getInterpolation(this.maxValue));
    }

    public double getMinPrice() {
        return this.minPrice;
    }

    public double getMaxPrice() {
        return this.maxPrice;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue.doubleValue();
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue.doubleValue();
    }

    private double roundValue(double originalValue, double interpolatedValue) {
        if (interpolatedValue < 100.0d || originalValue == this.minPrice || originalValue == this.maxPrice) {
            return interpolatedValue;
        }
        long longValue = (long) interpolatedValue;
        int power = 0;
        while (longValue >= 100) {
            longValue /= 10;
            power++;
        }
        return ((double) longValue) * Math.pow(10.0d, (double) power);
    }

    public String getCurrency() {
        return this.currency;
    }
}
