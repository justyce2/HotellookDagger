package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;

public class ViewOptionCriterion implements Criterion<Offer> {
    public boolean passes(Offer value) {
        return (value.getOptions() == null || value.getOptions().getView() == null) ? false : true;
    }
}
