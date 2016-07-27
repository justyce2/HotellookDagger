package com.hotellook.filters.items.criterion;

public interface Criterion<T> {
    boolean passes(T t);
}
