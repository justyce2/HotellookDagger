package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;

public class BreakfastOptionCriterion implements Criterion<Offer> {
    public boolean passes(Offer value) {
        return value.getOptions() != null && value.getOptions().hasBreakfast();
    }
}
