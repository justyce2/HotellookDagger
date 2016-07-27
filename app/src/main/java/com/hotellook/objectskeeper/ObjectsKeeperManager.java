package com.hotellook.objectskeeper;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

public final class ObjectsKeeperManager {
    private final Map<Integer, ObjectsKeeper> objectsKeepers;

    public ObjectsKeeperManager() {
        this.objectsKeepers = new HashMap();
    }

    @NonNull
    public ObjectsKeeper objectsKeeper(int id) {
        ObjectsKeeper objectsKeeper = (ObjectsKeeper) this.objectsKeepers.get(Integer.valueOf(id));
        if (objectsKeeper != null) {
            return objectsKeeper;
        }
        objectsKeeper = new ObjectsKeeper();
        this.objectsKeepers.put(Integer.valueOf(id), objectsKeeper);
        return objectsKeeper;
    }
}
