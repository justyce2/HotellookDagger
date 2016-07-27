package com.hotellook.filters.items;

import android.support.annotation.NonNull;
import com.hotellook.filters.FiltersSnapshot;
import com.hotellook.filters.PersistentFilters;
import com.hotellook.filters.items.criterion.Criterion;
import com.hotellook.search.SearchData;

public abstract class BoolFilterItem<T> extends FilterItem<T> implements Countable {
    private boolean checked;
    private int count;
    private final Criterion<T> criterion;
    private final boolean defaultState;

    @NonNull
    protected abstract String saveTag();

    public BoolFilterItem(@NonNull Criterion<T> criterion) {
        this.criterion = criterion;
        this.defaultState = false;
    }

    public BoolFilterItem(@NonNull Criterion<T> criterion, boolean defaultState) {
        this.criterion = criterion;
        this.defaultState = defaultState;
    }

    @NonNull
    public Criterion<T> getCriterion() {
        return this.criterion;
    }

    public void reset() {
        this.checked = this.defaultState;
    }

    public void setUp(SearchData searchData, PersistentFilters persistentFilters) {
        this.checked = this.defaultState;
        this.count = calculateCount(searchData);
    }

    public boolean inDefaultState() {
        return this.checked == this.defaultState;
    }

    public int hashCode() {
        return _hashCode();
    }

    public int _hashCode() {
        return this.checked ? 1 : 0;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void saveState(FiltersSnapshot snapshot) {
        snapshot.addData(saveTag(), Boolean.valueOf(this.checked));
    }

    public void restoreState(FiltersSnapshot snapshot) {
        this.checked = ((Boolean) snapshot.getData(saveTag())).booleanValue();
    }

    public void release() {
    }

    public int getCount() {
        return this.count;
    }

    protected int calculateCount(@NonNull SearchData data) {
        return 0;
    }
}
