package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.DistrictFilterItem;
import me.zhanghai.android.materialprogressbar.C1759R;

public class DistrictPresenter extends BoolFilterPresenter {
    private final DistrictFilterItem filterItem;

    public DistrictPresenter(@NonNull DistrictFilterItem filterItem) {
        super(filterItem);
        this.filterItem = filterItem;
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox, parent, false);
        ((TextView) view.findViewById(C1759R.id.title)).setText(this.filterItem.getDistrictName());
        ((TextView) view.findViewById(C1178R.id.count)).setText(String.valueOf(this.filterItem.getCount()));
        return view;
    }
}
