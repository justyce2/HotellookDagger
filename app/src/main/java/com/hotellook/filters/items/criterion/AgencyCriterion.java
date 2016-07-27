package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;

public class AgencyCriterion implements Criterion<Offer> {
    private final String agencyName;

    public AgencyCriterion(String agencyName) {
        this.agencyName = agencyName;
    }

    public boolean passes(Offer value) {
        return this.agencyName.equals(value.getGateName());
    }
}
