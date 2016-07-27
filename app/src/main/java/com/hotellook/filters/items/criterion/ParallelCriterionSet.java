package com.hotellook.filters.items.criterion;

import java.util.ArrayList;
import java.util.List;

public class ParallelCriterionSet<T> implements CriterionSet<T> {
    private final List<Criterion<T>> criteria;

    public ParallelCriterionSet() {
        this.criteria = new ArrayList();
    }

    public void add(Criterion<T> criterion) {
        if (criterion != null) {
            this.criteria.add(criterion);
        }
    }

    public boolean passes(T value) {
        for (Criterion<T> criterion : this.criteria) {
            if (criterion.passes(value)) {
                return true;
            }
        }
        return false;
    }

    public int getCount() {
        return this.criteria.size();
    }

    public boolean hasCriteria() {
        return !this.criteria.isEmpty();
    }
}
