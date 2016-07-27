package com.hotellook.filters.persistant;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.BoolFilterItem;
import com.hotellook.ui.screen.filters.presenters.CheckBoxPresenter;
import me.zhanghai.android.materialprogressbar.C1759R;

public class PersistentCheckboxFilterPresenter extends CheckBoxPresenter {
    private final BoolFilterItem<?> filterItem;
    private final PersistentBoolFilterItem persistentBoolFilterItem;
    @StringRes
    private final int titleId;

    public PersistentCheckboxFilterPresenter(PersistentBoolFilterItem persistentBoolFilterItem, BoolFilterItem<?> filterItem, int titleId) {
        this.persistentBoolFilterItem = persistentBoolFilterItem;
        this.filterItem = filterItem;
        this.titleId = titleId;
    }

    public boolean isChecked() {
        return this.persistentBoolFilterItem.enabled();
    }

    public void setChecked(boolean checked) {
        this.persistentBoolFilterItem.setEnabled(checked);
        this.filterItem.setChecked(checked);
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox, parent, false);
        ((TextView) view.findViewById(C1759R.id.title)).setText(this.titleId);
        view.findViewById(C1178R.id.count).setVisibility(8);
        return view;
    }
}
