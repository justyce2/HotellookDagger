package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;

public class PriceCriterion implements Criterion<Offer> {
    private final double maxPrice;
    private final double minPrice;

    public PriceCriterion(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public boolean passes(Offer value) {
        return value.getPrice() >= this.minPrice && value.getPrice() <= this.maxPrice;
    }
}
