package com.hotellook.filters.items.criterion;

import com.hotellook.core.api.pojo.search.Offer;
import com.hotellook.core.api.pojo.search.OptionsData;

public class HotelWebsiteAgencyCriterion implements Criterion<Offer> {
    public boolean passes(Offer value) {
        OptionsData options = value.getOptions();
        return options != null && options.hasHotelWebsite();
    }
}
