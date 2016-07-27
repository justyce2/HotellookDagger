package com.hotellook.filters.items.criterion;

public interface CriterionSet<T> extends Criterion<T> {
    void add(Criterion<T> criterion);

    boolean hasCriteria();
}
