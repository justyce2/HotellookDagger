package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import com.hotellook.filters.items.BoolFilterItem;

public abstract class BoolFilterPresenter extends CheckBoxPresenter {
    private final BoolFilterItem filterItem;

    public BoolFilterPresenter(@NonNull BoolFilterItem filterItem) {
        this.filterItem = filterItem;
    }

    public boolean isChecked() {
        return this.filterItem.isChecked();
    }

    public void setChecked(boolean checked) {
        this.filterItem.setChecked(checked);
    }
}
