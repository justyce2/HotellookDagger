package com.hotellook.search;

import com.hotellook.core.api.pojo.common.Poi;
import com.hotellook.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Pois {
    private final List<Poi> allPois;
    private final Map<Integer, List<Poi>> pois;

    public Pois(Map<Integer, List<Poi>> pois) {
        this.pois = pois;
        this.allPois = mergePois();
    }

    private List<Poi> mergePois() {
        List<Poi> allPois = new ArrayList();
        for (List<Poi> poisInLocation : this.pois.values()) {
            if (poisInLocation != null) {
                allPois.addAll(poisInLocation);
            }
        }
        return allPois;
    }

    public List<Poi> all() {
        return this.allPois;
    }

    public List<Poi> inLocation(int id) {
        return (List) CollectionUtils.getValue(this.pois, Integer.valueOf(id), Collections.emptyList());
    }

    public Set<Integer> locationIds() {
        return this.pois.keySet();
    }
}
