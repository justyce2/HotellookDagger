package com.hotellook.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewId {
    private AtomicInteger seq;

    public ViewId() {
        this.seq = new AtomicInteger(0);
    }

    public int getUniqueId() {
        return this.seq.incrementAndGet();
    }
}
