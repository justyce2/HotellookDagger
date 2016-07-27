package com.hotellook.objectskeeper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public final class ObjectsKeeper implements SaveStateObjectKeeper {
    private final Map<String, Object> cache;

    public ObjectsKeeper() {
        this.cache = new HashMap();
    }

    public void put(@NonNull String key, @NonNull Object object) {
        this.cache.put(key, object);
    }

    @Nullable
    public Object poll(@NonNull String key) {
        Object obj = this.cache.get(key);
        if (obj != null) {
            this.cache.remove(key);
        }
        return obj;
    }

    public void remove(@NonNull String key) {
        this.cache.remove(key);
    }

    public boolean contains(@NonNull String key) {
        return this.cache.containsKey(key);
    }

    public void clean() {
        this.cache.clear();
    }
}
