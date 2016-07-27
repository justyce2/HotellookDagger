package com.hotellook.search;

import android.support.annotation.NonNull;
import com.hotellook.core.api.pojo.search.Offer;
import java.util.List;
import java.util.Map;

public class Offers {
    private final Map<Long, List<Offer>> offers;

    public Offers(@NonNull Map<Long, List<Offer>> offers) {
        this.offers = offers;
    }

    public Map<Long, List<Offer>> all() {
        return this.offers;
    }
}
