package com.hotellook.search;

import com.hotellook.core.api.pojo.common.District;
import com.hotellook.utils.CollectionUtils;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Districts {
    private final Map<Integer, List<District>> districtsForLocations;

    public Districts(Map<Integer, List<District>> districtsForLocations) {
        this.districtsForLocations = districtsForLocations;
    }

    public List<District> all() {
        List<District> all = new LinkedList();
        for (List<District> districts : this.districtsForLocations.values()) {
            if (CollectionUtils.isNotEmpty(districts)) {
                all.addAll(districts);
            }
        }
        return all;
    }
}
