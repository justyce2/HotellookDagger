package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hotellook.filters.items.criterion.Criterion;

public abstract class FilterItem<T> implements BaseLogicItem {
    @NonNull
    protected abstract Criterion<T> getCriterion();

    @Nullable
    public Criterion<T> newCriterion() {
        if (enabled()) {
            return getCriterion();
        }
        return null;
    }

    public boolean enabled() {
        return !inDefaultState();
    }
}
