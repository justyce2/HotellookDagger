package com.hotellook.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class ExtrusiveFixedList<T> extends LinkedBlockingQueue<T> {
    private final int mFixSize;

    public ExtrusiveFixedList(int fixSize) {
        super(fixSize);
        this.mFixSize = fixSize;
    }

    public void put(T object) {
        if (size() >= this.mFixSize) {
            poll();
        }
        try {
            super.put(object);
        } catch (InterruptedException e) {
        }
    }
}
