package com.hotellook.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapOfLists<K, V> extends HashMap<K, List<V>> {
    public void add(K key, V value) {
        get((Object) key).add(value);
    }

    public List<V> get(Object key) {
        List<V> items = (List) super.get(key);
        if (items != null) {
            return items;
        }
        items = new ArrayList();
        put(key, items);
        return items;
    }

    public List<V> all() {
        List<V> result = new ArrayList();
        for (List<V> value : values()) {
            result.addAll(value);
        }
        return result;
    }
}
