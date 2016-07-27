package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;

public class HasPriceCriterion implements Criterion<Offer> {
    public boolean passes(Offer value) {
        return true;
    }
}
