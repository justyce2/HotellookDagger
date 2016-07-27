package com.hotellook.common.fresco;

import com.facebook.common.internal.Supplier;
import com.facebook.imagepipeline.cache.MemoryCacheParams;

public class BitmapMemoryCacheParamsSupplier implements Supplier<MemoryCacheParams> {
    private static final int FRESCO_CACHE_SIZE;

    static {
        FRESCO_CACHE_SIZE = ((int) Runtime.getRuntime().maxMemory()) / 4;
    }

    public MemoryCacheParams get() {
        return new MemoryCacheParams(FRESCO_CACHE_SIZE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, FRESCO_CACHE_SIZE, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }
}
