package com.hotellook.utils;

import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> Set<T> asSet(T... objects) {
        return new HashSet(Arrays.asList(objects));
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T, S> S getValue(Map<T, S> map, T key, S defaultValue) {
        return !map.containsKey(key) ? defaultValue : map.get(key);
    }
}
