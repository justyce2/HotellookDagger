package com.hotellook.ui.screen.filters.presenters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.hotellook.C1178R;
import com.hotellook.filters.items.NoStarsFilterItem;
import me.zhanghai.android.materialprogressbar.C1759R;

public class NoStarsPresenter extends BoolFilterPresenter {
    private final NoStarsFilterItem filterItem;

    public NoStarsPresenter(@NonNull NoStarsFilterItem filterItem) {
        super(filterItem);
        this.filterItem = filterItem;
    }

    public View createView(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(C1178R.layout.filter_item_checkbox, parent, false);
        ((TextView) view.findViewById(C1759R.id.title)).setText(C1178R.string.no_stars);
        ((TextView) view.findViewById(C1178R.id.count)).setText(String.valueOf(this.filterItem.getCount()));
        return view;
    }
}
